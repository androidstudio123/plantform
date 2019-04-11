package cn.crm.shiro.realm;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysFuncEntity;
import cn.crm.service.sys.SysAdminService;
import cn.crm.service.sys.SysFuncService;
import cn.crm.util.AdminEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysAdminService adminServcie;
    @Autowired
    private SysFuncService sysFuncService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("++++++++开始分配当前登陆用户所拥有的菜单权限+++++");
        SysAdminEntity adminEntity = (SysAdminEntity) principals.getPrimaryPrincipal();
        String adminId = adminEntity.getAdmin_id().toString();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Map<String, String> map = new HashMap<String, String>();
        map.put("admin_id", adminId);
        //获取角色对应的
        List<SysFuncEntity> resourceList = sysFuncService.findFunsByAdminId(map);
        for (SysFuncEntity resource : resourceList) {
            if (StringUtils.isNotEmpty(resource.getFun_url())) {
                info.addStringPermission(resource.getFun_url());
            }
        }
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authtoken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authtoken;
        String userName = token.getUsername();
        SysAdminEntity adminEntity = adminServcie.findByAdminName(userName);
        if (null != adminEntity) {
            //冻结
            if (1==adminEntity.getAdmin_status()) {
                throw new LockedAccountException();
            }
            //已删除
            if (2==adminEntity.getAdmin_status()) {
                throw new AuthenticationException();
            }

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(adminEntity, adminEntity.getAdmin_password().toCharArray(), ByteSource.Util.bytes(userName + "" + adminEntity.getAdmin_salt()), getName());
            Session session = SecurityUtils.getSubject().getSession();
            //放入session中
            session.setAttribute(AdminEntityUtil.ADMIN_SESSION_KEY, adminEntity);
            return authenticationInfo;
        } else {
            throw new UnknownAccountException();

        }
    }
}
