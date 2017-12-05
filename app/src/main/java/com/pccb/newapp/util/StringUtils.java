package com.pccb.newapp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author cgc
 */
public class StringUtils {

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return !(email == null || email.trim().length() == 0) && pattern.matcher(email).matches();
    }

    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 将double类型数字加上千分位，保留两位小数
     */
    public static String addStrThousands(double d) {
        DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
        return df.format(d);
    }

    /**
     * 将double类型数字加上千分位，不保留小数
     */
    private static String addStrThousandsNoDot(double d) {
        DecimalFormat df = new DecimalFormat("###,###,###,##0");
        String p = df.format(d);
        return p;
    }

    /**
     * 将String类型数字加上千分位，保留两位小数
     */
    public static String strTo2dotThousands(String str) {
        BigDecimal bigVal = new BigDecimal(str);
        return StringUtils.addStrThousands(bigVal.doubleValue());
    }

    /**
     * 将String类型数字加上千分位
     */
    public static String strAddThousands(String str) {
        String strValue = "";
        if (str.contains(".") && !str.contains(".00")) {
            strValue = strTo2dotThousands(str);
            strValue = strValue.replace(".00", "");
        } else {
            BigDecimal bigVal = new BigDecimal(str);
            strValue = StringUtils.addStrThousandsNoDot(bigVal.doubleValue());
        }
        return strValue;
    }

    /**
     * 将double类型数字保留两位小数
     */
    public static String add2dotStr(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * 手机号验证
     */
    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 通过源字符串重复生成N次组成新的字符串。
     */
    private static String repeat(String src, int num) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < num; i++) {
            s.append(src);
        }
        return s.toString();
    }

    /**
     * 前后保留num位，中间用*代替
     */
    public static String replaceStrWithStar(String src, int num) {
        String sReplace;
        int length = src.length();
        if (length > num * 2) {
            sReplace = src.substring(num, length - num);
            src = src.replace(sReplace, repeat("*", length - num * 2));
        }
        return src;
    }

    /**
     * 前保留startNum位，后保留endNum位,中间用*代替
     */
    public static String replaceStrWithStar(String src, int startNum, int endNum) {
        int length = src.length();
        if (length > (startNum + endNum)) {
            StringBuilder sb = new StringBuilder();
            sb.append(src.substring(0, startNum));
            int last = length - endNum;
            for (int i = 0; i < last; i++) {
                sb.append('*');
            }
            sb.append(src.substring(length - endNum, length));
            return sb.toString();
        }
        return src;
    }

    /**
     * index前面所有位替换成*号 保留index后面
     */
    public static String replaceStart(String src, int index) {
        if (src == null || index <= 0 | src.length() <= index) {
            return src;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append("*");
        }
        sb.append(src.substring(index, src.length()));
        return sb.toString();
    }

    /**
     * 保留index前面 后面所有位替换成*号
     */
    public static String replaceEnd(String src, int index) {
        if (src == null || index <= 0 | src.length() <= index) {
            return src;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(src.substring(0, index));
        for (int i = 0; i < src.length() - index; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
}