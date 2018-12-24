package com.cheng.disruptor.api.high;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author cheng
 *         2018/12/24 22:41
 */
public class Handler1 implements EventHandler<Trade>,WorkHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        onEvent(event);
    }

    @Override
    public void onEvent(Trade event) throws InterruptedException {
        System.out.println("handler 1 : SET NAME");
        event.setName("H1");
        Thread.sleep(1000);
    }
}
