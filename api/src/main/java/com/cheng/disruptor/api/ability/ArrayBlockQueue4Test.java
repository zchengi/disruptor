package com.cheng.disruptor.api.ability;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * ArrayBlockQueue 性能测试
 *
 * @author cheng
 *         2018/12/22 16:29
 */
@Slf4j
public class ArrayBlockQueue4Test {

    public static void main(String[] args) {

        final ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<>(Constants.EVENT_NUM_OHM);
        final long startTime = System.currentTimeMillis();

        // 向容器中添加元素
        new Thread(() -> {

            long i = 0;
            while (i++ < Constants.EVENT_NUM_OHM) {
                Data data = new Data(i, "c" + i);
                try {
                    queue.put(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {

            int k = 0;
            while (k++ < Constants.EVENT_NUM_OHM) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.currentTimeMillis();
            log.info("ArrayBlockingQueue costTime = {}ms", endTime - startTime);
        }).start();
    }
}
