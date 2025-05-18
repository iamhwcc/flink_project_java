package com.DataStream.codes;

import DataStream.mySource.MySourceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-17 星期六 22:58:55
 */
public class readMySource {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(new MySourceFunction()).setParallelism(2);
        source.print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
