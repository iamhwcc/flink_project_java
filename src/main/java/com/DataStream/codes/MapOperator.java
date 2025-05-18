package com.DataStream.codes;

import com.DataStream.mySource.MySourceFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-17 星期六 23:35:29
 */
public class MapOperator {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(new MySourceFunction()).setParallelism(1);
        source.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                int index = Integer.parseInt(s.split("_")[1]);
                if (index % 2 == 0) {
                    return s + "_hwc"; // 偶数
                } else {
                    return s + "_stalwart"; // 奇数
                }
            }
        }).print();

        env.execute();
    }
}
