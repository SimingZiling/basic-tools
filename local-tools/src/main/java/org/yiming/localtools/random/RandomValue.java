package org.yiming.localtools.random;

/**
 * 随机值
 */
public class RandomValue {

    /**
     * 获取长整型的
     * @param digits 随机数位数
     * @return 随机数
     */
    public static long randomLong(int digits){

        long randomLong = 0L;
        for (int i=0;i<digits;i++){
            randomLong = (long) (randomLong+(10*(Math.random()))*Math.pow(10,i));
        }
        return randomLong;

    }


}
