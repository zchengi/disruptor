package com.cheng.disruptor.api.quickstart;

import lombok.Data;

/**
 * 1. 创建 Event 对象
 *
 * @author cheng
 *         2018/12/22 18:46
 */
@Data
public class OrderEvent {

    /**
     * 订单价格
     */
    private long value;
}
