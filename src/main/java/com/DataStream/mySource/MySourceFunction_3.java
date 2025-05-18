package com.DataStream.mySource;

import com.DataStream.pojo.InputModel;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-18 星期日 14:01:39
 */
public class MySourceFunction_3 implements ParallelSourceFunction<List<InputModel>> {
    private boolean is_running = true;
    private static final int ARRAY_LENGTH = 4;
    private static final int SLEEP_TIME = 1000;

    @Override
    public void run(SourceContext<List<InputModel>> ctx) throws Exception {
        int[] age = new int[]{18, 21, 22, 28};
        String[] names = new String[]{"stalwart", "hwc", "raider", "nestor"};
        List<InputModel> buffer = new ArrayList<>();
        while (is_running) {
            int random = getRandomIndex(ARRAY_LENGTH);
            InputModel inputModel = InputModel.builder()
                    .username(names[random] + "source_3")
                    .age(age[random]).build();
            buffer.add(inputModel);

            if (buffer.size() == 5) {
                ctx.collect(buffer);
                buffer = new ArrayList<>();
            }

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new InterruptedException(e.getMessage());
            }
        }
    }

    @Override
    public void cancel() {
        this.is_running = false;
    }

    public int getRandomIndex(int length) {
        Random random = new Random();
        return random.nextInt(length);
    }
}
