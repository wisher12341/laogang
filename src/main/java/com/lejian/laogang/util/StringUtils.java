package com.lejian.laogang.util;

import com.google.gson.Gson;

public final class StringUtils extends org.apache.commons.lang.StringUtils{

    private static Gson gson = new Gson();

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()){
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0, 1).toLowerCase());
        for (int i = 1; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static String nameMask(String name){
        if (name.length()==2){
            return name.substring(0,1)+"*";
        }
        if (name.length()>=3){
            return name.substring(0,1)+"*"+name.substring(2);
        }
        return name;
    }

    private static String createAsterisk(int len) {
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<len;i++){
            sb.append("*");
        }
        return sb.toString();
    }

    public static <T> T fromGson(String str,Class<T> clazz){
        return gson.fromJson(str, clazz);
    }
}
