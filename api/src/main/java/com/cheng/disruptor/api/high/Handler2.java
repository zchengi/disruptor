package com.cheng.disruptor.api.high;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;

/**
 * @author cheng
 *         2018/12/24 22:47
 */
public class Handler2 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws InterruptedException {
        System.out.println("handler 2 : SET ID");
        event.setId(UUID.randomUUID().toString());
        Thread.sleep(2000);
    }
}
