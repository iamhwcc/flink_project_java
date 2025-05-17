package collector.flinkSQL;

import collector.udf.Arr2Tables;
import collector.udf.SplitFunction;
import collector.udf.String2Array;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-15 星期四 22:58:45
 */
public class UDFRegister {

    /**
     * UDF 注册函数
     * @param tableEnv
     */
    public static void setUpUDF(StreamTableEnvironment tableEnv) {
        tableEnv.createTemporaryFunction("String2Array", String2Array.class);
        tableEnv.createTemporaryFunction("Arr2Tables", Arr2Tables.class);
        tableEnv.createTemporaryFunction("SplitFunction", SplitFunction.class);
    }
}
