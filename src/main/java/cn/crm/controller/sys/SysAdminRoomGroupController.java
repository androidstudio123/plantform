package cn.crm.controller.sys;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysRoomgroupEntity;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.sys.SysAdminRoomgroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController @RequestMapping("/sysAdminRoomGroup") @Api(description = "管理员工房间组")
public class SysAdminRoomGroupController {

    @Autowired
    private SysAdminRoomgroupService sysAdminRoomgroupService;

    /**
     * 根据管理员id查询对应的房间组  sysAdminRoomGroup/findAdminRoomgroup
     */
    @GetMapping("/findAdminRoomgroup")
    @ApiOperation(notes = "根据管理员id查询对应的房间组",value = "根据管理员id查询对应的房间组")
    public ResultData findAdminRoomgroup(Integer parentId, HttpServletRequest request) {
        //调用查询方法
         List<SysRoomgroupEntity> adminRoomgroup = sysAdminRoomgroupService.findAdminRoomgroup(parentId);
        return new ResultData(ResultCode.SUCCESS.getCode(), true, ResultCode.SUCCESS.getMessage(), adminRoomgroup);
    }





}
