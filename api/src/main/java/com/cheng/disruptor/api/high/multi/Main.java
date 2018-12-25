package com.cheng.disruptor.api.high.multi;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.BasicExecutor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

/**
 * 多生产者多消费者
 *
 * @author cheng
 *         2018/12/25 15:36
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {

        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

        // 1. 创建 RingBuffer
        RingBuffer<Order> ringBuffer =
                RingBuffer.create(ProducerType.MULTI,
                        Order::new,
                        1024 * 1024,
                        new YieldingWaitStrategy());

        // 2. 通过 RingBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3. 创建多消费者
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        // 4. 构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<>(ringBuffer, sequenceBarrier,
                new EventExceptionHandler(), consumers);

        // 5. 设置多个消费者的 sequence 序号，用于单独统计消费进度，并且设置到 ringBuffer 中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 6. 启动 WorkPool
        workerPool.start(new BasicExecutor(threadFactory));

        // 7. 构建多个生产者
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            Producer producer = new Producer(ringBuffer);
            threadFactory.newThread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < 100; j++) {
                    producer.sendData(UUID.randomUUID().toString());
                }
            }).start();
        }

        Thread.sleep(2000);
        log.info("-----线程创建完毕，开始生产数据-----");
        latch.countDown();


        Thread.sleep(5000);
        log.info("消费者处理的任务总数: {}", Consumer.getCount());
    }
}
