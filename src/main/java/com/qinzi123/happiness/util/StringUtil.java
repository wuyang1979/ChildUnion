package com.qinzi123.happiness.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * @title: StringUtil
 * @package: com.qinzi123.happiness.util
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class StringUtil {
    public static String omitStringByLength(String value, int length) {
        if (length < CommConstant.SYMBOL_OMIT.length()) {
            throw new RuntimeException("omit length is to short");
        }
        String ret = value.trim();
        if (length < value.length()) {
            ret = value.substring(0, length + 1 - CommConstant.SYMBOL_OMIT.length()) + CommConstant.SYMBOL_OMIT;
        }
        return ret;
    }

    public static String omitStringByLength(int value, int length) {
        return omitStringByLength(String.valueOf(value), length);
    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static String dataFormat(Date date, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType, Locale.getDefault());

        return format.format(date);
    }

    public static boolean isDateTimeString(String date, String formatType) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType, Locale.getDefault());
        try {
            simpleDateFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentDateTime(String type) {
        SimpleDateFormat format = new SimpleDateFormat(type, Locale.CHINA);

        String ret = format.format(new Date());

        return ret;
    }

    public static String[] getArrayByList(List<String> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        int len = list.size();
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    public static String deletEndConnectSymbol(String str, String symbol) {
        if (str == null || str.length() <= symbol.length()) {
            return null;
        }
        int lastIndex = str.lastIndexOf(symbol);

        if (lastIndex == str.length() - symbol.length()) {
            str = str.substring(0, lastIndex);
        }

        return str;
    }

    public static String DecimalFormat(String format, double value) {
        String ret = null;

        DecimalFormat df = new DecimalFormat("#.00");

        ret = df.format(value);

        if (0 <= value && value < 1) {
            ret = df.format(value);
            ret = "0" + ret;
        } else if (0 > value && value > -1) {
            ret = df.format(0 - value);
            ret = "-0" + ret;
        }

        return ret;
    }

    public static String decodeStringToUTF(String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static boolean isEmpty(String str) {
        return null == str || str.isEmpty();
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(long[] array, char separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static long[] split(String series, char separator) {
        // 空值保护
        if (isEmpty(series)) {
            return new long[0];
        }
        String[] strs = series.split(String.valueOf(separator));
        long[] longSeries = new long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            longSeries[i] = Long.parseLong(strs[i]);
        }
        return longSeries;
    }

    public static String getRandomStr(int len) {
        if (len <= 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String secret(String val) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < val.length(); i++) {
            sb.append((char) (val.charAt(i) ^ Constants.SECRET_KEY));
        }
        return sb.toString();
    }
}
