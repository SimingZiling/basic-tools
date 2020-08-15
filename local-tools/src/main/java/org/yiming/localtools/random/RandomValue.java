package org.yiming.localtools.random;

/**
 * 随机值
 */
public class RandomValue {

    /**
     * 获取长整型的(该方法有问题)
     * @param digits 随机数位数
     * @return 随机数
     */
    public static long randomLong(int digits){
        long randomLong = 0L;
        for (int i=0;i<digits;i++){
            randomLong = (long) (randomLong+(10*(Math.random()))*Math.pow(10,i));
//            randomLong = (long) ((Math.random() * (9)) + 0)+(randomLong*10);
        }
        return randomLong;
    }

    /**
     * 通过最大数和最小数获取长整型随机数
     * @param max 最大数
     * @param min 最小数
     * @return 随机数
     */
    public static long randomLongByMaxAndMin(long max,long min){
        return (long) ((Math.random() * (max - min)) + min);
    }

//    public static void main(String[] args) {
////        for (int i = 0;i <= 100;i++){
////            System.out.println(randomLong(4));
////        }
//        int max = 9999;
//        int min = 1000;
//        for (int i=0;i<30;i++) {
////            System.out.println((int) ((Math.random() * (max - min)) + min));
////            System.out.println(randomLongByMaxAndMin(max,min));
//            System.out.println(randomLong(4));
//        }
//
//    }

}
