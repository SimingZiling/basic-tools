package org.yiming.localtools.crypto;


import org.yiming.localtools.conversion.ByteUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 安全散列算法
 */
public class SHA {




//    /**
//     * sha256_HMAC加密
//     * @param message 消息
//     * @param key  秘钥
//     * @return 加密后字符串
//     */
//    public static String sha256HMAC(String message, String key) throws InvalidKeyException, NoSuchAlgorithmException {
//        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
//        sha256_HMAC.init(secret_key);
//        byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
//        System.out.println(Base64.getEncoder().encodeToString(bytes));
//        return byteArrayToHexString(bytes);
//    }

    /**
     * sha256HMAC 加密
     * @param message 参数
     * @param key 密钥
     * @param base64 是否生成base64字符串
     * @return 加密信息
     */
    public static String sha256HMAC(String message,String key,Boolean base64) throws InvalidKeyException, NoSuchAlgorithmException {
        if(base64 != null && base64){
            return ByteUtil.byteArrayToBash64String(sha256HMAC(message,key.getBytes()));
        }else {
            return ByteUtil.byteArrayToHexString(sha256HMAC(message, key.getBytes()));
        }
    }

    /**
     * sha256HMAC 加密
     * @param message 加密信息
     * @param bitKey 二进制密钥
     * @return 二进制加密信息
     */
    public static byte[] sha256HMAC(String message,byte[] bitKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(bitKey, "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return sha256_HMAC.doFinal(message.getBytes());
    }
}
