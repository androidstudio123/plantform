package cn.crm.shiro.matcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * shiro 自定义密码登陆匹配
 */
@Slf4j
public class ShiroPassMatcher  extends HashedCredentialsMatcher {

    private static Map<String,AtomicInteger> passwordRetryCache = new HashMap<>();

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        EasyTypeToken tk = (EasyTypeToken) authcToken;
        if(tk.getType().equals(LoginType.NOPASSWD)){
            return true;
        }
        boolean matches = super.doCredentialsMatch(authcToken, info);
        return matches;
    }



}
