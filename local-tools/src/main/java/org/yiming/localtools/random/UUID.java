package org.yiming.localtools.random;

/**
 * 获取UUID
 */
public class UUID {

    /**
     * 获取不带横线的UUID
     * @return UUID
     */
    public static String getUUID(){
        // 获取UUID
        String  uuid = java.util.UUID.randomUUID().toString();
        // 去掉横线
        return uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
    }
}
