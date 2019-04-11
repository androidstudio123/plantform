package cn.crm.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import cn.crm.shiro.matcher.ShiroPassMatcher;
import cn.crm.shiro.sessionmanager.DefaultHeaderSessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.crm.filter.ShiroLoginFilter;
import cn.crm.shiro.realm.MyRealm;

/**
 * shiro 配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("【进行了权限过滤。。。。】");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> filtsMap = new LinkedHashMap<String, Filter>();
        filtsMap.put("authc", new ShiroLoginFilter());
        //filters.put("corsFilter", new RestFilter());
        shiroFilterFactoryBean.setFilters(filtsMap);
        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/dologin/*", "anon");
        filterChainDefinitionMap.put("/valicode", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "anon");
//		filterChainDefinitionMap.put("/**", "authc");
//		shiroFilterFactoryBean.setLoginUrl("/wxindex.html");
        // 未授权界面;
        //shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public MyRealm myRealm(ShiroPassMatcher shiroPassMatcher) {
        MyRealm myShiroRealm = new MyRealm();
        myShiroRealm.setCredentialsMatcher(shiroPassMatcher);
        return myShiroRealm;
    }


    @Bean
    public ShiroPassMatcher hashedCredentialsMatcher() {
        ShiroPassMatcher shiroPassMatcher = new ShiroPassMatcher();
        shiroPassMatcher.setHashAlgorithmName("MD5");// 散列算法:MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512等。
        shiroPassMatcher.setHashIterations(2);// 散列的次数，默认1次， 设置两次相当于 md5(md5(""));
        return shiroPassMatcher;
    }

    @Bean
    public SecurityManager securityManager(MyRealm myRealm,DefaultHeaderSessionManager defaultHeaderSessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        securityManager.setSessionManager(defaultHeaderSessionManager);
        return securityManager;
    }

    @Bean
    public DefaultHeaderSessionManager defaultHeaderSessionManager() {
        DefaultHeaderSessionManager defaultHeaderSessionManager = new DefaultHeaderSessionManager();
        //session 过期时间设置
        //defaultSessionManager.setCacheManager(cacheManager);
//        DefaultWebSecurityManager
//        defaultHeaderSessionManager.setGlobalSessionTimeout(60*60*12);
        return defaultHeaderSessionManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }



}
