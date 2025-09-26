package com.hooper.ugg.util;

public class StringUtil {

    /**
     * 生成工资单文件名：格式为 “[姓名]起始日期 - 截止日期”
     */
    public static String buildPayslipFileName(String realName, String startDate, String endDate) {
        return String.format("[%s]%s - %s", realName, startDate, endDate);
    }

    /**
     * 通用拼接：加空格
     */
    public static String joinWithSpace(String... parts) {
        return String.join(" ", parts);
    }

    /**
     * 通用拼接：加中划线
     */
    public static String joinWithDash(String... parts) {
        return String.join(" - ", parts);
    }

}
