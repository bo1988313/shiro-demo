package com.zhl.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @author 70716
 */
public class MySecurityManager {
    public static void main(String[] args) {
        //创建安全管理器
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //设置rems
        defaultSecurityManager.setRealm(
                new IniRealm("classpath:shiro.ini"));
        //安全管理工具设置安全管理器
        SecurityUtils.setSecurityManager(defaultSecurityManager
        );
        //获取对象主体
        Subject subject = SecurityUtils.getSubject();


        //设置用户名密码
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        try {
            //验证
            System.out.println("状态信息:" + subject.isAuthenticated());
            subject.login(token);
            System.out.println("状态信息:" + subject.isAuthenticated());
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
