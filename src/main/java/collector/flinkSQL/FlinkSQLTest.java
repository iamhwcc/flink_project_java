package collector.flinkSQL;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-15 星期四 22:58:23
 */
public class FlinkSQLTest {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        // 注册UDF
        UDFRegister.setUpUDF(tableEnv);



    }
}
