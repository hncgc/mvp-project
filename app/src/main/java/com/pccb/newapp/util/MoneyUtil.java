package com.pccb.newapp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by YanJun on 2017/8/3.
 */

public class MoneyUtil {

    private static final DecimalFormat rateFormat = new DecimalFormat("0.00");
    private static final DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");

    public static int compare(String num1, String num2) {
        return new BigDecimal(num1).compareTo(new BigDecimal(num2));
    }

    public static String mul(String num1, String num2) {
        if (null == num1 || "".equals(num1) || null == num2 || "".equals(num2)) {
            return "0";
        }

        return new BigDecimal(num1).multiply(new BigDecimal(num2)).toPlainString();
    }

    public static String sub(String num1, String num2) {
        if (null == num1 || "".equals(num1) || null == num2 || "".equals(num2)) {
            return "0";
        }

        return new BigDecimal(num1).subtract(new BigDecimal(num2)).toPlainString();
    }

    public static String add(String num1, String num2) {
        if (null == num1 || "".equals(num1) || null == num2 || "".equals(num2)) {
            return "0";
        }

        return new BigDecimal(num1).add(new BigDecimal(num2)).toPlainString();
    }

    /**
     * 根式金额
     *
     * @param money
     * @return
     */
    public static String formatMoney(String money) {
        if (null == money || "".equals(money)) {
            money = "0";
        }
        return moneyFormat.format(new BigDecimal(money));
    }

    /**
     * 格式化年利率
     *
     * @param rate 利率
     * @return
     */
    public static String formatRate(String rate) {
        if (null == rate || "".equals(rate)) {
            return "";
        }

        return rateFormat.format(new BigDecimal(rate).multiply(BigDecimal.valueOf(100)));
    }

}
