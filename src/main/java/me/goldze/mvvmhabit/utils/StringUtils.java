package me.goldze.mvvmhabit.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Map;

/**
 * Created by goldze on 2017/5/14.
 * 字符串相关工具类
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否不为 null
     *
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final String str) {
        return (str != null && str.length() != 0);
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 判断内容, 是否属于特定字符串开头 ( 对比大小写 )
     *
     * @param str  待校验的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(
            final String str,
            final String... args
    ) {
        return isStartsWith(false, str, args);
    }

    /**
     * 判断内容, 是否属于特定字符串开头
     *
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(
            final boolean isIgnore,
            final String str,
            final String... args
    ) {
        if (!isEmpty(str) && args != null && args.length != 0) {
            String tempString = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                tempString = tempString.toLowerCase();
            }
            for (String value : args) {
                // 判断是否为 null, 或者长度为 0
                if (!isEmpty(value)) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = value.toLowerCase();
                        // 判断是否属于 val 开头
                        if (tempString.startsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 开头
                        if (tempString.startsWith(value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串结尾 ( 对比大小写 )
     * @param str  待校验的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(
            final String str,
            final String... args
    ) {
        return isEndsWith(false, str, args);
    }

    /**
     * 判断内容, 是否属于特定字符串结尾
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(
            final boolean isIgnore,
            final String str,
            final String... args
    ) {
        if (!isEmpty(str) && args != null && args.length != 0) {
            String tempString = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                tempString = tempString.toLowerCase();
            }
            for (String value : args) {
                // 判断是否为 null, 或者长度为 0
                if (!isEmpty(value)) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = value.toLowerCase();
                        // 判断是否属于 val 结尾
                        if (tempString.endsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 结尾
                        if (tempString.endsWith(value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设置部分文本单独设置一种颜色
     * @param sourceText
     * @param secondTexts
     * @param secondColor
     * @return
     */
    public static SpannableStringBuilder setSecondColor(String sourceText,String[] secondTexts, String secondColor){
        SpannableStringBuilder ssb = new SpannableStringBuilder(sourceText);

        if(secondTexts!=null&&secondTexts.length>0){
            for(String str:secondTexts){
                if(str!=null){
                    int startIndex = sourceText.indexOf(str);
                    if(startIndex!=-1){
                        int endIndex = startIndex+str.length();
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(secondColor));
                        ssb.setSpan(colorSpan, startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                /**
                    //单独设置字体大小
                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(70);
                    // 相对于默认字体大小的倍数,这里是1.2倍
                    // RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan((float) 1.2);
                    ssb.setSpan(sizeSpan, 7, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //设置删除线
                    StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                    //设置下划线
                    UnderlineSpan underlineSpan = new UnderlineSpan();
                    //同时设置字体颜色、点击事件
                    ClickableSpan clickableSpan = new ClickableSpan() {

                        @Override
                        public void onClick(@NonNull View widget) {

                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint paint) {
                            paint.setColor(Color.parseColor("#365899"));
                            paint.setUnderlineText(false);
                        }
                    };
                 **/
                }
            }
        }

        return ssb;
    }

    /**
     * 设置部分文本内容的第二颜色和点击事件，把文本内容和点击事件按key-value的方式放入map中作为参数
     * @param textView
     * @param sourceText
     * @param tcmap
     * @param secondColor
     */
    public static void setSecondColorAndListener(TextView textView,String sourceText, Map<String, View.OnClickListener> tcmap, String secondColor){
        SpannableStringBuilder ssb = new SpannableStringBuilder(sourceText);
        if(tcmap!=null&&tcmap.size()>0){
            for (Map.Entry<String, View.OnClickListener> entry : tcmap.entrySet()) {
                String str = entry.getKey();
                int startIndex = sourceText.indexOf(str);
                if(startIndex!=-1){
                    int endIndex = startIndex+str.length();
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor(secondColor));
                    ssb.setSpan(colorSpan, startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    final View.OnClickListener onClickListener = entry.getValue();
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View view) {
                            if(onClickListener!=null){
                                onClickListener.onClick(view);
                            }
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    };
                    ssb.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        textView.setText(ssb);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.parseColor("#00000000"));
    }
}
