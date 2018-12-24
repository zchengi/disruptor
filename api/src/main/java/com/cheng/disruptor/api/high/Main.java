package com.cheng.disruptor.api.high;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Disruptor 高级操作
 *
 * @author cheng
 *         2018/12/24 22:00
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // 线程工厂用于创建创建线程，提交任务
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // 1.构建 Disruptor
        Disruptor<Trade> disruptor =
                new Disruptor<>(Trade::new,
                        1024 * 1024,
                        threadFactory,
                        ProducerType.SINGLE,
                        new BusySpinWaitStrategy());

        // 2. 把消费者设置到 Disruptor 中 handlerEventWith

        // 2.1 串行操作
//        disruptor.handleEventsWith(new Handler1())
//                .then(new Handler2())
//                .then(new Handler3());

        // 2.2 并行操作
        disruptor.handleEventsWith(new Handler1(), new Handler2(), new Handler3());

        // 3. 启动 Disruptor
        disruptor.start();

        long begin = System.currentTimeMillis();

        // 4. 生产数据
        CountDownLatch latch = new CountDownLatch(1);
        threadFactory.newThread(new TradePublisher(disruptor, latch)).start();

        // 阻塞
        latch.await();

        disruptor.shutdown();

        System.out.println("总耗时: " + (System.currentTimeMillis() - begin) + "ms");
    }
}
