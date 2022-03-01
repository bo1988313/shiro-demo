package com.zhl.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Md5加密算法的realm
 *
 * @author 70716
 */
public class Md5Realm extends AuthorizingRealm {
    /**
     * 授权
     *
     * @param principals principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String mainPrincipal = (String) principals.getPrimaryPrincipal();

        //模拟数据库查询用户权限
        List<String> roleList = new ArrayList<>();
        List<String> resourceList = new ArrayList<>();
        if (USERNAME.equals(mainPrincipal)) {
            roleList.add("user");
            roleList.add("admin");
            resourceList.add("voice:*");
            resourceList.add("user:*:*");
            resourceList.add("ocr:baidu:*");
            resourceList.add("produce:select:01");

        }

        //角色授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);

        //资源授权
        simpleAuthorizationInfo.addStringPermissions(resourceList);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param token token
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //模拟数据库匹配
        String principal = (String) token.getPrincipal();
        if (USERNAME.equals(principal)) {
            //MD5加密
//            return new SimpleAuthenticationInfo(principal, MD5STR, this.getName());
            //MD5+salt
//            return new SimpleAuthenticationInfo(principal, MD5ANDSALT, ByteSource.Util.bytes(SALT), this.getName());
            //MD5+salt+散列1024
            return new SimpleAuthenticationInfo(principal, MD5ANDSALTANDHASH, ByteSource.Util.bytes(SALT), this.getName());
        }
        return null;
    }

    private static String USERNAME = "admin";

    private static String MD5STR = "21232f297a57a5a743894a0e4a801fc3";

    private static String MD5ANDSALT = "25a96b83f1593475d1b40da779371b08";

    private static String MD5ANDSALTANDHASH = "bedff84cc9e93aa45b265fe235dbacce";

    private static String SALT = "weihuashangyeyinhang";

    private static int HASHINT = 1024;
}
