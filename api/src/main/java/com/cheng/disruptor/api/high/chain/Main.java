package com.cheng.disruptor.api.high.chain;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

/**
 * Disruptor 高级操作
 *
 * @author cheng
 *         2018/12/24 22:00
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // 线程工厂用于创建创建线程，提交任务
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

        // 1.构建 Disruptor
        Disruptor<Trade> disruptor =
                new Disruptor<>(Trade::new,
                        1024 * 1024,
                        threadFactory,
                        ProducerType.SINGLE,
                        new BlockingWaitStrategy());

        // 2. 把消费者设置到 Disruptor 中 handlerEventWith01

        // 2.1 串行操作
        /*disruptor.handleEventsWith(new Handler1())
                .then(new Handler2())
                .then(new Handler3());*/

        // 2.2 并行操作
//        disruptor.handleEventsWith(new Handler1(), new Handler2(), new Handler3());

        // 2.3 菱形操作(1)
//        disruptor.handleEventsWith(new Handler1(), new Handler2()).handleEventsWith(new Handler3());

        // 2.3 菱形操作(2)
//        EventHandlerGroup<Trade> eventHandlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
//        eventHandlerGroup.then(new Handler3());

        // 2.3 六边形操作
        Handler1 handler1 = new Handler1();
        Handler2 handler2 = new Handler2();
        Handler3 handler3 = new Handler3();
        Handler4 handler4 = new Handler4();
        Handler5 handler5 = new Handler5();
        // 1->2 和 4->5 并行执行，然后执行 3
        disruptor.handleEventsWith(handler1, handler4);
        disruptor.after(handler1).then(handler2);
        disruptor.after(handler4).then(handler5);
        disruptor.after(handler2, handler5).then(handler3);

        // 3. 启动 Disruptor
        disruptor.start();

        long begin = System.currentTimeMillis();

        // 4. 生产数据
        CountDownLatch latch = new CountDownLatch(1);
        threadFactory.newThread(new TradePublisher(disruptor, latch)).start();

        // 阻塞
        latch.await();

        disruptor.shutdown();

        log.info("总耗时: {}ms", System.currentTimeMillis() - begin);
    }
}
