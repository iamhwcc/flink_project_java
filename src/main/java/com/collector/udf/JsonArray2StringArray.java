package com.collector.udf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.Arrays;

/**
 * @author: stalwarthuang
 * @create: 2025-08-02 23:22
 * @description:
 **/

public class JsonArray2StringArray extends ScalarFunction {

    /**
     * 将 json 提取出来的数组字符串转换为 String[]
     *
     * @param jsonArrayStr 数组字符串
     * @return String[]
     */
    public String[] eval(String jsonArrayStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if (jsonArrayStr == null) {
            return new String[0];
//            return new;
        }
        return mapper.readValue(jsonArrayStr, String[].class);
    }
}
