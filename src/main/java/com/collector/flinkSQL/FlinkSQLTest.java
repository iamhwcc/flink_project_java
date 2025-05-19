package com.collector.flinkSQL;

import com.DataStream.mySource.MySourceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-15 星期四 22:58:23
 */
public class FlinkSQLTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        DataStreamSource<String> source = env.addSource(new MySourceFunction()).setParallelism(1);

        tableEnv.createTemporaryView("table1", source, Schema.newBuilder()
                .column("f0", DataTypes.STRING())
                .build());

        tableEnv.executeSql("""
                        select 
                            f0,
                            if(arr1 is null, concat('.', arr0), f0) as src_table
                        from 
                        (
                            select
                                f0,
                                SPLIT_INDEX(f0, '.', 0) as arr0,
                                SPLIT_INDEX(f0, '.', 1) as arr1
                            from table1
                        )
                """).print();

        env.execute();
    }
}
