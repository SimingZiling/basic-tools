package org.yiming.localtools.crypto;

import org.yiming.localtools.conversion.ByteUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    /**
     * 获取MD5值
     * @param parameter 参数
     * @return MD5
     */
    public static String getMd5(String parameter){
        byte[] btInput = parameter.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 使用指定的字节更新摘要
        assert messageDigest != null;
        messageDigest.update(btInput);
        return ByteUtil.byteArrayToHexString(messageDigest.digest());
    }

    /**
     * 获取带参数的MD5值
     * @param parameter 参数
     * @param salt 盐值
     * @return 带盐的MD5
     */
    public static String getMd5(String parameter,String salt) {
        return getMd5(parameter+salt);
    }

    /**
     * 获取循环加密的MD5值
     * @param parameter 参数
     * @param cycle 循环次数
     * @return 循环后的MD5
     */
    public static String getMd5(String parameter,long cycle){
        for (int i = 1;i <= cycle;i++){
            parameter = getMd5(parameter);
        }
        return parameter;
    }

    /**
     * 获取带参数循环加密的MD5值
     * @param parameter 参数
     * @param salt 盐
     * @param cycle 循环次数
     * @return 带盐循环的MD5值
     */
    public static String getMd5(String parameter,String salt,long cycle){
        for (int i = 1;i <= cycle;i++){
            parameter = getMd5(parameter,salt);
        }
        return parameter;
    }

}
