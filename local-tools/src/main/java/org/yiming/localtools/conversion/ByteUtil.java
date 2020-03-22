package org.yiming.localtools.conversion;

import java.util.Base64;

/**
 * 字节
 */
public class ByteUtil {

    /**
     * 字节数组转换成字符串
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; bytes!=null && n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * 字节数组转换成Bash64字符串
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String byteArrayToBash64String(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

}
