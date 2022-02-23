package com.zhl.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * shiro中md5加密的使用
 *
 * @author 70716
 */
public class Md5Test {

    /**
     * md5 加密
     *
     * @param password password
     * @return 密码
     */
    public static String md5(String password) {
        Md5Hash md5Hash = new Md5Hash(password);
        return md5Hash.toHex();
    }

    /**
     * md5 + 盐 加密
     *
     * @param password password
     * @return 密码
     */
    public static String md5AndSalt(String password, String salt) {
        Md5Hash md5Hash = new Md5Hash(password, salt);
        return md5Hash.toHex();
    }

    /**
     * md5 + 盐 + 散列 加密
     *
     * @param password password
     * @return 密码
     */
    public static String md5AndSaltAndHash(String password, String salt, int hashIterations) {
        Md5Hash md5Hash = new Md5Hash(password, salt, hashIterations);
        return md5Hash.toHex();
    }

}
