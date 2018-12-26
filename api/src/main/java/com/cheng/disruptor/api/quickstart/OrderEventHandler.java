package com.cheng.disruptor.api.quickstart;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 3. 监听事件类（具体的消费者）
 *
 * @author cheng
 *         2018/12/22 18:50
 */
@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
        log.info("消费: {}", event.getValue());
    }
}
