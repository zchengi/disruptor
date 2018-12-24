package com.cheng.disruptor.api.high.chain;


import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/24 22:41
 */
@Slf4j
public class Handler3 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) {
        log.info("handler 3 : NAME: {}, ID: {}, INSTANCE: {}",
                event.getName(), event.getId(), event.toString());
    }
}
