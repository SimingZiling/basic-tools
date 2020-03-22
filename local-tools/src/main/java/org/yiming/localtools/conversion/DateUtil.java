package org.yiming.localtools.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换
 */
public class DateUtil extends Date {

    /**
     * 字符串转日期类型
     * @param dateString 日期字符串
     * @param pattern 日期格式
     * @return 日期
     * @throws ParseException 日期格式异常
     */
    public  Date stringToDate(String dateString, String pattern) throws ParseException{
        return new SimpleDateFormat(pattern).parse(dateString);
    }

    /**
     * 日期转字符串
     * @param date 日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public String dateToString(Date date,String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 通过生日获取年龄
     * @param birthDay 生日
     * @return 年龄
     */
    public int getAgeBybirthDay(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生日期能大于当前日期！");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    /**
     * 修改时间
     * @param date 基础时间
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 修改后的时间
     */
    public Date modificationDate(Date date,int year,int month,int day,int hour,int minute,int second){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //修改年
        calendar.add(Calendar.YEAR,year);
        //修改月
        calendar.add(Calendar.MONTH,month);
        //修改日
        calendar.add(Calendar.DATE,day);
        //修改时
        calendar.add(Calendar.HOUR,hour);
        //修改分
        calendar.add(Calendar.MINUTE,minute);
        //修改秒
        calendar.add(Calendar.SECOND,second);
        return calendar.getTime();
    }

    /**
     * 修改天数
     * @param date 基础时间
     * @param day 修改的天数
     * @return 修改后的时间
     */
    public  Date modifyDay(Date date,int day){
        return modificationDate(date,0,0,day,0,0,0);
    }

    /**
     * 修改年数
     * @param date 基础时间
     * @param year 修改的年数
     * @return 修改后的时间
     */
    public  Date modifyYear(Date date,int year) {
        return modificationDate(date,year,0,0,0,0,0);
    }

    /**
     * 修改月数
     * @param date 基础时间
     * @param month 修改的月数
     * @return 修改后的时间
     */
    public  Date modifyMonth(Date date,int month){
        return modificationDate(date,0,month,0,0,0,0);
    }

    /**
     * 修改小时数
     * @param date 基础时间
     * @param hour 修改的小时数
     * @return 修改后的时间
     */
    public  Date modifyHour(Date date,int hour) {
        return modificationDate(date,0,0,0,hour,0,0);
    }

    /**
     * 修改分钟数
     * @param date 基础时间
     * @param minute 修改的分钟数
     * @return 修改后的时间
     */
    public  Date modifyMinute(Date date,int minute){
        return modificationDate(date,0,0,0,0,minute,0);
    }

    /**
     * 修改秒数
     * @param date 基础时间
     * @param second 修改的秒数
     * @return 修改后的时间
     */
    public  Date modifySecond(Date date,int second){
        return modificationDate(date,0,0,0,0,0,second);
    }

    /**
     * 通过时间获取星座
     * @param date 时间
     * @return 星座名称
     */
    public String getConstellationByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        return day < dayArr[month -1 ] ? constellationArr[month -1].getChineseName() : constellationArr[month].getChineseName();
    }

    /**
     * 月份时间顺序
     */
    private final int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    /**
     * 星座列表
     */
    private final Constellation[] constellationArr = {
            Constellation.AQUARIUS, Constellation.PISCES,
            Constellation.ARIES, Constellation.TAURUS,
            Constellation.GEMINI, Constellation.CANCER,
            Constellation.LEO, Constellation.VIRGO,
            Constellation.LIBRA, Constellation.SCORPIO,
            Constellation.SAGITTARIUS,Constellation.CAPRICORN
    };

    /**
     * 星座枚举
     */
    enum Constellation{
        ARIES(1,"白羊座"),
        TAURUS(2,"金牛座"),
        GEMINI(3,"双子座"),
        CANCER(4,"巨蟹座"),
        LEO(5,"狮子座"),
        VIRGO(6,"处女座"),
        LIBRA(7,"天秤座"),
        SCORPIO(8,"天蝎座"),
        SAGITTARIUS(9,"射手座"),
        CAPRICORN(10,"摩羯座"),
        AQUARIUS(11,"水瓶座"),
        PISCES(12,"双鱼座 ");

        private int code;
        private String chineseName;

        Constellation(int code, String chineseName) {
            this.code = code;
            this.chineseName = chineseName;
        }

        public int getCode() {
            return code;
        }

        public String getChineseName() {
            return chineseName;
        }
    }

}
