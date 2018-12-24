package com.cheng.disruptor.api.high.chain;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 * @author cheng
 *         2018/12/24 22:21
 */
public class TradePublisher implements Runnable {

    private static int PUBLISH_COUNT = 1;
    private Disruptor<Trade> disruptor;
    private CountDownLatch latch;

    public TradePublisher(Disruptor<Trade> disruptor, CountDownLatch latch) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {

        for (int i = 0; i < PUBLISH_COUNT; i++) {
            TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();
            // 提交数据（新方式）
            disruptor.publishEvent(tradeEventTranslator);
        }

        // 唤醒
        latch.countDown();
    }
}

