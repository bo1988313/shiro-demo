package com.example.springbootshirojsp2.config;

import com.example.springbootshirojsp2.shiro.MyRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author 70716
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建shiroFilter  负责拦截请求
     *
     * @param securityManager securityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filter = new HashMap<>(10);
        shiroFilterFactoryBean.setFilters(filter);
        //配置系统受限资源
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/index.jsp", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //设置系统跳转页面
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        return shiroFilterFactoryBean;
    }

    /**
     * 创建安全管理器
     *
     * @param realm          realm
     * @param sessionManager sessionManager
     * @return DefaultWebSecurityManager
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realm") Realm realm, SessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        //给安全管理器设置sessionManager
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }

    /**
     * 创建自定义realm
     *
     * @return Realm
     */
    @Bean("realm")
    public Realm getRealm() {
        return new MyRealm();
    }

    /**
     * 创建sessionManager
     *
     * @return DefaultWebSessionManager
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //设置session过期时间3600s
        defaultWebSessionManager.setGlobalSessionTimeout(3600000L);
        return defaultWebSessionManager;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}