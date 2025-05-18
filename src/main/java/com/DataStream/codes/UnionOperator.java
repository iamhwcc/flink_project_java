package com.DataStream.codes;

import com.DataStream.mySource.MySourceFunction_1;
import com.DataStream.mySource.MySourceFunction_2;
import com.DataStream.mySource.MySourceFunction_3;
import com.DataStream.pojo.InputModel;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-18 星期日 13:32:44
 */
public class UnionOperator {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(1);

        DataStreamSource<List<InputModel>> source1 = env.addSource(new MySourceFunction_1()).setParallelism(1);
        DataStreamSource<List<InputModel>> source2 = env.addSource(new MySourceFunction_2()).setParallelism(1);
        DataStreamSource<List<InputModel>> source3 = env.addSource(new MySourceFunction_3()).setParallelism(1);
        // union 合并同结构数据
        DataStream<List<InputModel>> unionData = source1.union(source2, source3);

        unionData.print();
        env.execute();
    }
}
