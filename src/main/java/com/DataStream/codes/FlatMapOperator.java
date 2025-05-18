package com.DataStream.codes;

import DataStream.mySource.MySourceFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-18 星期日 00:15:37
 */
public class FlatMapOperator {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(new MySourceFunction()).setParallelism(1);

        source.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                Arrays.stream(s.split("_")).forEach(collector::collect);
            }
        }).print();
        env.execute();
    }
}
