package com.cheng.disruptor.api.ability;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/22 17:14
 */
@Slf4j
public class DataConsumer implements EventHandler<Data> {

    private long startTime;

    private int i;

    public DataConsumer() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onEvent(Data event, long sequence, boolean endOfBatch) {
        i++;
        if (i == Constants.EVENT_NUM_OHM) {
            long endTime = System.currentTimeMillis();
            log.info("Disruptor costTime = {}ms", endTime - startTime);
        }
    }
}
