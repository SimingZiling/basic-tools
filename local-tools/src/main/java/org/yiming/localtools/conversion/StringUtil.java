package org.yiming.localtools.conversion;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 首字母小写
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String initialLowercase(String str){
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static boolean isNull(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
}

