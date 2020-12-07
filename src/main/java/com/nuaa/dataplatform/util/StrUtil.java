package com.nuaa.dataplatform.util;

public class StrUtil {
    /** 去除首尾的空格、下划线、冒号 */
    public static String clearTrim(String str) {
        char t; int lenth = 0;
        for (int i = 0; i < str.length(); i++) {
            t = str.charAt(i);
            if (t == ' ' || t == '_' || t == '：' || t == ':' || t == '　') {
                lenth++;
            } else {
                break;
            }
        }
        str = str.substring(lenth);
        lenth = str.length();
        for (int i = str.length() - 1; i >= 0; i--) {
            t = str.charAt(i);
            if (t == ' ' || t == '_' || t == '：' || t == ':' || t == '　') {
                lenth--;
            } else {
                break;
            }
        }
        str = str.substring(0, lenth);
        return str;
    }

    //把 yyyy年m月d日 改成 yyyy-mm-dd
    public static String dateFormat(String str) {
        if (isEmpty(str)) {
            return "1970-01-01";
        }
        str = str.replace('年', '-').replace('月', '-').replace("日", "");
        String[] dateStrings = str.split("-");
        if (dateStrings.length != 3) {
            return "1970-01-01";
        }
        return addFrontZero(dateStrings[0], 4) + "-" +
               addFrontZero(dateStrings[1], 2) + "-" +
               addFrontZero(dateStrings[2], 2);
    }

    public static String addFrontZero(String str, int total) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < total - str.length(); i++) {
            strBuilder.append("0");
        }
        return strBuilder.append(str).toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.length() == 0;
    }
}
