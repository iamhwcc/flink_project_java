package com.DataStream.codes;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-18 星期日 23:36:07
 */
public class fromSocket {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        env.setParallelism(1);
        DataStreamSource<String> socketSource1 = env.socketTextStream("localhost", 7878);
        SingleOutputStreamOperator<Tuple3<String, String, Long>> map1 = socketSource1.map(new MapFunction<String, Tuple3<String, String, Long>>() {
            @Override
            public Tuple3<String, String, Long> map(String value) throws Exception {
                String[] arr = value.split(",");
                return Tuple3.of(arr[0], arr[1], Long.parseLong(arr[2]));
            }
        });
        DataStreamSource<String> socketSource2 = env.socketTextStream("localhost", 7879);
        SingleOutputStreamOperator<Tuple3<String, String, Long>> map2 = socketSource2.map(new MapFunction<String, Tuple3<String, String, Long>>() {
            @Override
            public Tuple3<String, String, Long> map(String value) throws Exception {
                String[] arr = value.split(",");
                return Tuple3.of(arr[0], arr[1], Long.parseLong(arr[2]));
            }
        });

        tableEnv.createTemporaryView("leftTable", map1, Schema.newBuilder()
                .column("f0", DataTypes.STRING())
                .column("f1", DataTypes.STRING())
                .column("f2", DataTypes.BIGINT())
                .columnByExpression("rt", "to_timestamp_ltz(f2, 3)")
                .watermark("rt", "rt - interval '0' second")
                .build());

        tableEnv.createTemporaryView("rightTable", map2, Schema.newBuilder()
                .column("f0", DataTypes.STRING())
                .column("f1", DataTypes.STRING())
                .column("f2", DataTypes.BIGINT())
                .columnByExpression("rt", "to_timestamp_ltz(f2, 3)")
                .watermark("rt", "rt - interval '0' second")
                .build());

        tableEnv.executeSql("""
                select a.f0, a.f1, a.f2, b.f0, b.f1, b.f2
                from leftTable a, rightTable b
                where a.f0 = b.f0
                and a.rt between b.rt - interval '2' second and b.rt + interval '3' second
                """).print();

        env.execute("Socket Data Source");
    }
}
