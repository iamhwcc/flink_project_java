package DataStream.codes;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-17 星期六 21:27:36
 */
public class wordCount {
    private static final Logger log = LoggerFactory.getLogger(wordCount.class);

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(1);

        DataStreamSource<String> wordSource = env.readTextFile("/Users/hwc/Documents/Flink Project/flink_project_java/src/main/java/DataStream/mySource/words.txt");
        SingleOutputStreamOperator<Tuple2<String, Integer>> tuple = wordSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) {
                String[] arr = s.split(" ");
                Arrays.stream(arr).forEach(word -> collector.collect(Tuple2.of(word, 1)));
            }
        });
        tuple.keyBy(0).sum(1).print();
        try {
            env.execute();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}