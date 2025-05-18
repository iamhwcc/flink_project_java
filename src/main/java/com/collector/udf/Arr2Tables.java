package com.collector.udf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-15 星期四 23:57:22
 */
public class Arr2Tables extends TableFunction<Row> {
    @FunctionHint(output = @DataTypeHint("ROW<src_table STRING>"))
    public void eval(String[] arr) {
        for (String src_table : arr) {
            collect(Row.of(src_table));
        }
    }
}
