package com.cheng.disruptor.api.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * 2.  创建工厂类，用于实例化 Event 对象
 *
 * @author cheng
 *         2018/12/22 18:48
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {

    /**
     * 这个方法就是为了返回空的数据对象 (Event)
     */
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
