package com.cheng.disruptor.api.high.chain;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/24 22:41
 */
@Slf4j
public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        onEvent(event);
    }

    @Override
    public void onEvent(Trade event) throws InterruptedException {
        log.info("handler 1 : SET NAME");
        Thread.sleep(1000);
        event.setName("H1");
    }
}
