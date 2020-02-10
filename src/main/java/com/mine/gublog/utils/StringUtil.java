package com.mine.gublog.utils;

public class StringUtil {
    private StringUtil() {
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String uncapitalize(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 成员变量name字符串转换
     *
     * @return
     */
    public static String fieldnameCovert(String field) {
        if(field.length()==0){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        // fieldName -> field_name
        int start = 0;
        int i;
        for (i = 0; i < field.length(); i++) {
            if ('A' <= field.charAt(i) && field.charAt(i) <= 'Z') {
                stringBuilder.append(field.substring(start, i).toLowerCase()).append('_');
                start = i;
            }
        }
        stringBuilder.append(field.substring(start, i).toLowerCase()).append('_');
        return stringBuilder.substring(0, stringBuilder.length()-1).toString();
    }
}
