package com.pccb.newapp.util;

import java.math.BigDecimal;

/**
 * 提供精确的浮点数运算，包括加减乘除和四舍五入。
 */
public class ArithUtils {
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    // 这个类不能实例化
    private ArithUtils() {

    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算 截取
     * 当发生除不尽的情况时，由scale参数指 定精度，以后的数字不四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要截取到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divFloor(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位 截取（不舍入）
     *
     * @param v     需要截取的数字
     * @param scale 小数点后保留几位
     * @return 截取后的结果
     */
    public static double floor(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 两个双BigDecimal数的比较
     *
     * @param val1
     * @param val2
     * @return
     */
    public static int bigCompare(BigDecimal val1, BigDecimal val2) {
        int result = 0;
        if (val1.compareTo(val2) < 0) {
            //第二位数大
            result = -1;
        }
        if (val1.compareTo(val2) == 0) {
            //两位数一样大
            result = 0;
        }
        if (val1.compareTo(val2) > 0) {
            //第一位数大
            result = 1;
        }
        return result;
    }

    /**
     * 两个双double数的比较
     *
     * @param vald1
     * @param vald2
     * @return
     */
    public static int compare(double vald1, double vald2) {
        int result = 0;
        BigDecimal val1 = new BigDecimal(vald1);
        BigDecimal val2 = new BigDecimal(vald2);
        if (val1.compareTo(val2) < 0) {
            //第二位数大
            result = -1;
        }
        if (val1.compareTo(val2) == 0) {
            //两位数一样大
            result = 0;
        }
        if (val1.compareTo(val2) > 0) {
            //第一位数大
            result = 1;
        }
        return result;
    }

};
