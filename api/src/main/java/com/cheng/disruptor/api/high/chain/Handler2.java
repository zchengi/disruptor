package com.cheng.disruptor.api.high.chain;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author cheng
 *         2018/12/24 22:47
 */
@Slf4j
public class Handler2 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws InterruptedException {
        log.info("handler 2 : SET ID");
        Thread.sleep(2000);
        event.setId(UUID.randomUUID().toString());
    }
}
