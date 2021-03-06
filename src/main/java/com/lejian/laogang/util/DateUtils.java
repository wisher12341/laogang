package com.lejian.laogang.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };
    private final static String[] zodiaces = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊",
            "猴", "鸡", "狗", "猪" };

    public final static DateTimeFormatter YYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter YYMMDDHHMMSS_1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public final static DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public final static DateTimeFormatter YY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter YY_MM = DateTimeFormatter.ofPattern("yyyy-MM");


    /**
     * 根据出生日期 计算星座
     * @return
     */
    public static String getConstellation(LocalDate birthday) {
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    /**
     * 通过生日计算属相
     * @param birthday
     * @return
     */
    public static String getZodiac(LocalDate birthday) {
        int year =birthday.getYear();
        if (year < 1900) {
            return "未知";
        }
        int start = 1900;
        return zodiaces[(year - start) % zodiaces.length];
    }


    public static int birthdayToAge(LocalDate birthday){
        if(birthday==null){
            return 0;
        }
        return calculateTwoDateYears(LocalDate.now(),birthday);
    }

    public static int calculateTwoDateYears(LocalDate now, LocalDate birthday){
        int yearNow = now.getYear();
        int monthNow = now.getMonthValue();
        int dayOfMonthNow = now.getDayOfMonth();

        int yearBirth = birthday.getYear();
        int monthBirth = birthday.getMonthValue();
        int dayOfMonthBirth = birthday.getDayOfMonth();

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


    public static String format(Timestamp timestamp, DateTimeFormatter format) {
        return timestamp.toLocalDateTime().format(format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss -> timestamp
     * @param time
     * @return
     */
    public static Timestamp toTimeStamp(String time) {
        return Timestamp.valueOf(time);
    }

    public static LocalDate stringToLocalDate(String date, DateTimeFormatter dateTimeFormatter) {
        return LocalDate.parse(date, dateTimeFormatter);
    }

    /**
     * 日期校验
     * @param format
     * @param data
     * @return
     */
    public static boolean isCorrect(String format, String data) {
        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        try {
            formatter.parse(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
