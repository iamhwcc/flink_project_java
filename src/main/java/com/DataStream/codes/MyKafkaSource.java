package com.DataStream.codes;

import com.DataStream.protoPojo.EventLog;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-18 星期日 14:44:30
 */
public class MyKafkaSource {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(1);

        // DataStream API Kafka
        KafkaSource<String> fromKafka = KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("mytopic")
                .setGroupId("mygroup")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema(StandardCharsets.UTF_8))
                .build();

        DataStreamSource<String> source = env.fromSource(fromKafka, WatermarkStrategy.noWatermarks(), "MyKafkaSource").setParallelism(2);
        source.print();

        // Protobuf kafka
        KafkaSource<EventLog> fromKafka1 = KafkaSource.<EventLog>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("mytopic")
                .setGroupId("mygroup")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new ProtobufAbstractDeserializationSchema())
                .build();

        env.execute();
    }

    private static class ProtobufAbstractDeserializationSchema extends AbstractDeserializationSchema<EventLog> {
        @Override
        public EventLog deserialize(byte[] bytes) throws IOException {
            return EventLog.parseFrom(bytes);
        }
    }

}

