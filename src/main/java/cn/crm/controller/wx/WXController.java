package cn.crm.controller.wx;

import cn.crm.controller.wx.WXUtils.WXDATA;
import cn.crm.controller.wx.WXUtils.WXResult_A;
import cn.crm.controller.wx.WXUtils.WXResult_B;
import cn.crm.entity.SysUserEntity;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.user.SysUserService;
import cn.crm.util.IdGenerator;
import cn.crm.util.PropertiesUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wxapp")
@Api(description = "微信手机端用户接入")
public class WXController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 微信手机端用户接入   返回前端数据
     * /wxapp/getWXuserInfo
     *
     * @param code
     */
    @GetMapping("/getWXuserInfo")
    @ApiOperation(value = "微信手机端用户接入", notes = "微信手机端用户接入")
    @ApiImplicitParam(name = "code", value = "微信code", required = false, paramType = "query", dataType = "String")
    @ResponseBody
    public ResultData getWXuserInfo(HttpServletRequest request, HttpServletResponse response, String code) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(code)) {
            //获取accesstoken
            WXResult_A wxResult_a = WXDATA.getAccessToken(code);
            if (null != wxResult_a.getAccess_token() & null != wxResult_a.getOpenid()) {
                //获取用户信息
                WXResult_B wxUserInfo = WXDATA.getWXUserInfo(wxResult_a.getAccess_token(), wxResult_a.getOpenid());
                if (wxUserInfo != null) {
                    String openid = wxUserInfo.getOpenid();
                    //openid 为用户openid
//                    SysUserEntity select = sysUserService.findByObject(new SysUserEntity(openid));
                    Example example = new Example(SysUserEntity.class);
                    example.createCriteria().andEqualTo("user_openId", openid);
                    List<SysUserEntity> sysUserEntities = sysUserService.queryExampleForList(example);
                    //用户非首次使用平台
                    if (sysUserEntities != null && sysUserEntities.size() != 0) {
                        SysUserEntity select = sysUserEntities.get(0);
                        request.getSession().setAttribute("userEntity", select);
//                        //查询用户权限
//                        List<Object> stringObjectMap = staffMapper.selectAppUserAuthId(roleId);
//                        map.put("userInfo", jsonObject);
//                        map.put("auth", stringObjectMap);
                        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), select);
                    }
                    //首次使用添加
                    SysUserEntity sysUserEntity = new SysUserEntity(
                            null,
                            wxUserInfo.getNickname(),
                            null,
                            wxUserInfo.getProvince() + wxUserInfo.getCity(),
                            0,
                            new Date(),
                            wxUserInfo.getHeadimgurl(),
                            wxUserInfo.getOpenid(),
                            null
                    );
                    String save = sysUserService.save(sysUserEntity);
                    if (!"success".equals(save)) {
                        return new ResultData(ResultCode.ERROR.getCode(), true, ResultCode.ERROR.getMessage(), null);
                    }
                    SysUserEntity userEntity = sysUserService.findByPrimaryKey(sysUserEntity.getUser_id());
                    request.getSession().setAttribute("userEntity", userEntity);
                    return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), userEntity);
                }
                return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, "获取用户信息失败!", null);
            }
            return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, "获取的用户access_token为空,微信登录失败!", null);
        }
        return new ResultData(ResultCode.EMPTYPARAMS.getCode(), false, "用户code获取不到,接入失败!", null);
    }


    @GetMapping("getSign")
    @ApiOperation(notes = "获取签名", value = "获取签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "网页地址", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "noncestr", value = "随机字符串", required = true, dataType = "string", paramType = "query")
    })
    public ResultData getSign(String url,String timestamp,String noncestr) {
        //获取appid
        String appid = PropertiesUtil.getValue("APPID");
        String secret= PropertiesUtil.getValue("APPSECRET");
        Map<String, Object> map2 = new HashMap<>();
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url1, HttpMethod.GET, null, String.class);
        Map map = JSON.parseObject(exchange.getBody(), Map.class);
        String access_token = (String) map.get("access_token");
        System.out.println("获取到的token为:" + map.get("access_token"));
        String url2 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        ResponseEntity<String> exchange1 = restTemplate.exchange(url2, HttpMethod.GET, null, String.class);
        Map map1 = JSON.parseObject(exchange1.getBody(), Map.class);
        String ticket = (String) map1.get("ticket");
        System.out.println("获取到的ticket为:" + ticket);
//        String noncestr = IdGenerator.idGen();
//
//        String timestamp= System.currentTimeMillis() / 1000+"";
//        String[] split = timestamp.split(".");
//        String substring = timestamp.substring(0, split.length);
//        String substring = timestamp.substring(0, timestamp.length() - 3);
        String sign = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(sign.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            map2.put("signature", hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        map2.put("jsapi_ticket", ticket);
        map2.put("noncestr", noncestr);
        map2.put("timestamp", timestamp);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), map2);
    }

}
