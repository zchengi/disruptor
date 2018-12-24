package com.cheng.disruptor.api.high.chain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Disruptor 中的 Event
 *
 * @author cheng
 *         2018/12/24 22:00
 */
@Data
public class Trade {

    private String id;

    private String name;

    private double price;

    private AtomicInteger count = new AtomicInteger(0);
}
