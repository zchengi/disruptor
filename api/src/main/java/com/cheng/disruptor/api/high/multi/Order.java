package com.cheng.disruptor.api.high.multi;

import lombok.Data;

/**
 * Disruptor 中的 Event
 *
 * @author cheng
 *         2018/12/25 15:38
 */
@Data
public class Order {

    private String id;

    private String name;

    private double price;
}
