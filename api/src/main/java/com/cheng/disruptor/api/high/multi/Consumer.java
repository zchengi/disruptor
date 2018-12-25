package com.cheng.disruptor.api.high.multi;

import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者
 *
 * @author cheng
 *         2018/12/25 15:43
 */
@Slf4j
public class Consumer implements WorkHandler<Order> {

    private static AtomicInteger count = new AtomicInteger(0);
    private Random random = new Random();
    private String consumerId;


    public Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(Order event) throws InterruptedException {
        Thread.sleep(random.nextInt(5));
        log.info("当前消费者: {}, 消费信息ID: {}", this.consumerId, event.getId());
        count.incrementAndGet();
    }

    public static int getCount() {
        return count.get();
    }
}
