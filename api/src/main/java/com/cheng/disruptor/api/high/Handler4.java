package com.cheng.disruptor.api.high;


import com.lmax.disruptor.EventHandler;

/**
 * @author cheng
 *         2018/12/24 22:41
 */
public class Handler4 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) {
        System.out.println("handler 4 : SET PRICE");
        event.setPrice(17D);
    }
}
