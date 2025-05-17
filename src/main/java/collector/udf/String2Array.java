package collector.udf;

import org.apache.flink.table.functions.ScalarFunction;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-15 星期四 23:00:04
 */
public class String2Array extends ScalarFunction {
    public String[] eval(String str) {
        String[] arr = str.trim().split(",");
        return arr;
    }
}
