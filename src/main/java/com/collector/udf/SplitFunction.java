package com.collector.udf;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-16 星期五 00:13:32
 */

import org.apache.flink.table.functions.ScalarFunction;

/**
 * Split字符串UDF函数
 * 用于将字符串按照指定分隔符分割成字符串数组
 */
public class SplitFunction extends ScalarFunction {

    /**
     * 将输入字符串按照指定分隔符分割成字符串数组
     * @param str 输入字符串
     * @param delimiter 分隔符
     * @return 分割后的字符串数组
     */
    public String[] eval(String str, String delimiter) {
        if (str == null || delimiter == null) {
            return new String[0];
        }
        return str.split(delimiter);
    }
}
