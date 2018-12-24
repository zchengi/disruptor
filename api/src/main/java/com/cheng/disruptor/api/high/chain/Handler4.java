package com.cheng.disruptor.api.high.chain;


import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/24 22:41
 */
@Slf4j
public class Handler4 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws InterruptedException {
        log.info("handler 4 : SET PRICE");
        Thread.sleep(1000);
        event.setPrice(17D);
    }
}
