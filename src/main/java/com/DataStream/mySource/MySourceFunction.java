package com.DataStream.mySource;

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-17 星期六 22:55:27
 */

// ParallelSourceFunction 支持多并行度source读取
// SourceFunction 仅支持并行度 1
public class MySourceFunction implements ParallelSourceFunction<String> {
    private boolean is_running = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        int i = 0;
        while (this.is_running) {
            if(i % 2 == 0) {
                ctx.collect("[\"table1\", \"table2\", \"table3\"]");
            }
            i++;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        this.is_running = false;
    }
}
