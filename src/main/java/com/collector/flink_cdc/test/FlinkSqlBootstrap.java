package com.collector.flink_cdc.test;

import com.collector.flinkSQL.UDFRegister;
import com.collector.flink_cdc.utils.SqlUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.util.List;

import static com.collector.flink_cdc.Constants.FileConstants.DDL_PATH_PREFIX;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-14 星期三 23:54:06
 */
public class FlinkSqlBootstrap {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setString("rest.port", "9091");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        UDFRegister.setUpUDF(tableEnv);
        env.enableCheckpointing(3000); // Flink CDC 2.0 必须开检查点才能采集到 Change Data


        List<String> ddlSqls = SqlUtils.extractDDLSqls(DDL_PATH_PREFIX + "com/collector/flink_cdc/test/ddl");
        List<String> etlSqls = SqlUtils.extractETLSqls(DDL_PATH_PREFIX + "com/collector/flink_cdc/test/etl");

        for (String ddlSql : ddlSqls) {
//            System.out.println(ddlSql);
            System.out.println("****************************************************************");
            tableEnv.executeSql(ddlSql);
        }

        for (String etlSql : etlSqls) {
//            System.out.println(etlSql);
            System.out.println("****************************************************************");
            tableEnv.executeSql(etlSql);
        }
    }
}
