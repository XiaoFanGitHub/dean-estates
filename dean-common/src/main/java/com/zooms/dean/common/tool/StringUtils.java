package com.zooms.dean.common.tool;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author slacrey
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 判断字符串中是否存在指定的字符串
     *
     * @param seq       原始用于比较的字符串
     * @param searchSeq 知道的用于查找的字符串
     * @return 若存在返回true 反之返回false
     */
    public static boolean exist(Object seq, Object searchSeq) {
        int _index = StringUtils.indexOf(seq + "", searchSeq + "");
        return _index >= 0;
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (StringUtils.isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : str.toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(StringUtils.trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 处理url
     * <p>
     * url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
     *
     * @param url url字符串
     * @return 转换后的url字符串
     */
    public static String handelUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        if (url.equals("") || url.startsWith("http://")
                || url.startsWith("https://")) {
            return url;
        } else {
            return "http://" + url.trim();
        }
    }

    /**
     * 分割并且去除空格
     *
     * @param str  待分割字符串
     * @param sep  分割符
     * @param sep2 第二个分隔符
     * @return 如果str为空，则返回null。
     */
    public static String[] splitAndTrim(String str, String sep, String sep2) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (!StringUtils.isBlank(sep2)) {
            str = StringUtils.replace(str, sep2, sep);
        }
        String[] arr = StringUtils.split(str, sep);
        // trim
        for (int i = 0, len = arr.length; i < len; i++) {
            arr[i] = arr[i].trim();
        }
        return arr;
    }

    /**
     * 文本转html
     *
     * @param txt text文本
     * @return html文本
     */
    public static String txt2htm(String txt) {
        if (StringUtils.isBlank(txt)) {
            return txt;
        }
        StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
        char c;
        boolean doub = false;
        for (int i = 0; i < txt.length(); i++) {
            c = txt.charAt(i);
            if (c == ' ') {
                if (doub) {
                    sb.append(' ');
                    doub = false;
                } else {
                    sb.append("&nbsp;");
                    doub = true;
                }
            } else {
                doub = false;
                switch (c) {
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    case '\n':
                        sb.append("<br/>");
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 剪切文本。如果进行了剪切，则在文本后加上"..."
     *
     * @param s   剪切对象。
     * @param len 编码小于256的作为一个字符，大于256的作为两个字符。
     * @return 剪切后加上".."的文本
     */
    public static String textCut(String s, int len, String append) {
        if (s == null) {
            return null;
        }
        int slen = s.length();
        if (slen <= len) {
            return s;
        }
        // 最大计数（如果全是英文）
        int maxCount = len * 2;
        int count = 0;
        int i = 0;
        for (; count < maxCount && i < slen; i++) {
            if (s.codePointAt(i) < 256) {
                count++;
            } else {
                count += 2;
            }
        }
        if (i < slen) {
            if (count > maxCount) {
                i--;
            }
            if (!StringUtils.isBlank(append)) {
                if (s.codePointAt(i - 1) < 256) {
                    i -= 2;
                } else {
                    i--;
                }
                return s.substring(0, i) + append;
            } else {
                return s.substring(0, i);
            }
        } else {
            return s;
        }
    }

    /**
     * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
     *
     * @param str
     * @param search
     * @return
     */
    public static boolean contains(String str, String search) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
            return false;
        }
        String reg = StringUtils.replace(search, "*", ".*");
        Pattern p = Pattern.compile(reg);
        return p.matcher(str).matches();
    }

    public static boolean containsKeyString(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.contains("'") || str.contains("\"") || str.contains("\r")
                || str.contains("\n") || str.contains("\t")
                || str.contains("\b") || str.contains("\f");
    }

    // 将""和'转义
    public static String replaceKeyString(String str) {
        if (containsKeyString(str)) {
            return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
                    "\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
                    "\b", "\\b").replace("\f", "\\f");
        } else {
            return str;
        }
    }

    public static String getSuffix(String str) {
        int splitIndex = str.lastIndexOf(".");
        return str.substring(splitIndex + 1);
    }

}
