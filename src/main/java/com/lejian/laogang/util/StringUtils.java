package com.lejian.laogang.util;

public final class StringUtils extends org.apache.commons.lang.StringUtils{

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
        if(name ==null)return name;
        if(name.length()<=1) return name+"*";
        return name.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1"+createAsterisk(name.length()-1));
    }

    private static String createAsterisk(int len) {
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<len;i++){
            sb.append("*");
        }
        return sb.toString();
    }
}
