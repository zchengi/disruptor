package com.cheng.disruptor.api.high.chain;

import com.lmax.disruptor.EventTranslator;

import java.util.Random;

/**
 * Event 具体操作
 *
 * @author cheng
 *         2018/12/24 22:42
 */
public class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();

    @Override
    public void translateTo(Trade event, long sequence) {
        this.generateTrade(event);
    }

    private void generateTrade(Trade event) {
        event.setPrice(random.nextDouble() * 9999);
    }
}
