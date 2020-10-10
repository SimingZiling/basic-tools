package org.yiming.localtools.verify;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 身份证
 */
public class IdCard {

    private static final  int OLD_ID_CARD_NUMBER_LENGTH = 15;// 久身份证位数

    private static final  int NEW_ID_CARD_NUMBER_LENGTH = 18; // 新身份证位数

    private static final int[] VERIFY_CODE_WEIGHT ={ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; // 18位身份证中，各个数字的生成校验码时的权值

    private static final char[] VERIFY_CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 18位身份证中最后一位校验码

    private static final  String BIRTH_DATE_FORMAT = "yyyyMMdd";// 身份证中生日格式


    /**
     * 验证中国身份证号
     * @param idCardNumber 身份证号
     * @return true 为通过校验
     */
    public static boolean checkChineseIdCard(String idCardNumber){
        if(idCardNumber != null && !idCardNumber.equals("")){
            // 去掉身份证号的空格
            idCardNumber = idCardNumber.trim();
            if(idCardNumber.length() == OLD_ID_CARD_NUMBER_LENGTH){
                idCardNumber = generateNewIdCardNumber(idCardNumber);
            }
            if(idCardNumber.length() == NEW_ID_CARD_NUMBER_LENGTH){
                return validateChineseIdCard(idCardNumber);
            }
        }
        return false;
    }

    /**
     * 验证中国身份证
     * @param idCardNumber 身份证号码
     * @return 验证结果
     */
    private static boolean validateChineseIdCard(String idCardNumber) {

        // 身份证基础验证
        if(!idCardNumber.matches("\\d{15}(\\d{2}[0-9xX])?")){
            return false;
        }
        // 验证校验码
        if(calculateVerifyCode(idCardNumber) != idCardNumber.charAt(NEW_ID_CARD_NUMBER_LENGTH - 1)){
            return false;
        }
        // 验证生日
        try {
            String birthdayPart = getBirthDayPart(idCardNumber);
            Date birthDate = new SimpleDateFormat(BIRTH_DATE_FORMAT).parse(birthdayPart);
            // 验证生日是否生成时间 如果没有生成则表示生日不正确
            if(birthDate == null){
                return false;
            }
            // 如果身份证生日在当天之后的话也不正确，没有人先拿身份证再出生的
            if(!birthDate.before(new Date())){
                return false;
            }
            // 在1900年之前出生的也不能通过，据当前120岁，估计岁互联网操作不是很熟悉了都
            if(!birthDate.after(new Date(-2209017600000L))){
                return false;
            }
            // 年月日正确性验证，月份[1,12],日期[1,31],还需要校验闰年、大月、小月的情况时
            String realBirthdayPart = new SimpleDateFormat(BIRTH_DATE_FORMAT).format(birthDate);
            if(!birthdayPart.equals(realBirthdayPart)){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取生日
     * @param idCardNumber 身份证
     * @return 生日
     */
    public static String getBirthDayPart(String idCardNumber) {
        return idCardNumber.substring(6, 14);
    }

    /**
     * 生成新的身份证
     * @param oldIdCardNumber 旧身份证
     * @return 新身份证
     */
    public static String generateNewIdCardNumber(String oldIdCardNumber){
        StringBuilder buf = new StringBuilder(NEW_ID_CARD_NUMBER_LENGTH);
        buf.append(oldIdCardNumber.substring(0, 6));
        // 添加生日年的前两位，2000年已经更换身份证，不用考虑20的情况 建国1949年，也不用考虑19以前的情况
        buf.append("19");
        buf.append(oldIdCardNumber.substring(6));
        buf.append(IdCard.calculateVerifyCode(buf));
        return buf.toString();
    }

    /**
     * 计算校验码
     * @param idCardNumber 身份证号码
     * @return 校验码
     */
    private static char calculateVerifyCode(CharSequence idCardNumber){
        int sum = 0;
        for (int i = 0; i < NEW_ID_CARD_NUMBER_LENGTH -1; i++){
            char ch = idCardNumber.charAt(i);
            sum += (ch - '0') * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

}
