package com.cheng.disruptor.api.high;


import com.lmax.disruptor.EventHandler;

/**
 * @author cheng
 *         2018/12/24 22:41
 */
public class Handler3 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) {
        System.out.println("handler 3 : NAME: " + event.getName()
                + ", ID: " + event.getId()
                + ", INSTANCE: " + event.toString());
    }
}
