package com.cheng.disruptor.api.quickstart;

import com.lmax.disruptor.EventHandler;

/**
 * 3. 监听事件类（具体的消费者）
 *
 * @author cheng
 *         2018/12/22 18:50
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        System.out.println("消费: " + event.getValue());
    }
}
