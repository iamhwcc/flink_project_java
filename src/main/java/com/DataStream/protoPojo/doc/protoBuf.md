# How to use Protobuf in a Maven Project

This document explains how to use **Protocol Buffers (Protobuf)** in a Maven-based Java project, including how to configure the plugin, compile `.proto` files, and generate Java classes.

---

## 📦 Step 1: Add Dependencies

In your `pom.xml`, add the Protobuf Java runtime dependency:

```xml
<dependencies>
    <!-- Protobuf Java Runtime -->
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>3.19.4</version>
    </dependency>
</dependencies>
```

---

## 🔧 Step 2: Add Protobuf Maven Plugin

Add the `protobuf-maven-plugin` to automatically compile `.proto` files into Java classes during the build process.

```xml
<extensions>
    <extension>
        <groupId>kr.motd.maven</groupId>
        <artifactId>os-maven-plugin</artifactId>
        <version>1.7.0</version>
    </extension>
</extensions>

<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.5.1</version>
    <configuration>
        <!--proto文件放置目录-->
        <protoSourceRoot>${basedir}/src/main/java/com/proto</protoSourceRoot>
        <protocArtifact>com.google.protobuf:protoc:3.19.4:exe:${os.detected.classifier}</protocArtifact>
        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
        <clearOutputDirectory>false</clearOutputDirectory>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>
                    compile
                </goal>
                <goal>
                    compile-custom
                </goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## 📁 Step 3: Create `.proto` Files

Create a `.proto` file in `${basedir}/src/main/java/com/proto`, for example:

```proto
syntax = "proto3";
package com;
option java_package = "com.DataStream.protoPojo";
option java_outer_classname = "EventLogProto"; // 生成的Java类名
option java_multiple_files = true; // 编译后生成多个Message类

message EventLog {
  string request_id = 1;

  uint64 timestamp = 2;

  string event_id = 50;
  string event_name = 53;

}
```

---

## 🛠 Step 4: Compile `.proto` Files

Run the following command to compile the `.proto` files and generate Java classes:

![img.png](img.png)

After compilation, the generated Java classes will be in `com.DataStream.protoPojo`:

```
option java_package = "com.DataStream.protoPojo";
```

Make sure to add this path to your IDE as a **generated source folder**.

---

## ✅ Step 5: Use the Generated Java Classes in Flink Kafka

```java
// Protobuf kafka
KafkaSource<EventLog> fromKafka1 = KafkaSource.<EventLog>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("mytopic")
                .setGroupId("mygroup")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new ProtobufAbstractDeserializationSchema())
                .build();
...
private static class ProtobufAbstractDeserializationSchema extends AbstractDeserializationSchema<EventLog> {
    @Override
    public EventLog deserialize(byte[] bytes) throws IOException {
        return EventLog.parseFrom(bytes);
    }
}
```

