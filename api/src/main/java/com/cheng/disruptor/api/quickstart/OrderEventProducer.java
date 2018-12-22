package com.cheng.disruptor.api.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 5. 订单生产者
 *
 * @author cheng
 *         2018/12/22 19:24
 */
public class OrderEventProducer {

    private final RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 投递数据
     *
     * @param data
     */
    public void sendData(ByteBuffer data) {

        // 1. 在生产者发送消息的时候，首先需要从 ringBuffer 里面获取一个可用的序号
        long sequence = ringBuffer.next();

        try {
            // 2. 根据这个序号，找到具体的 "OrderEvent" 元素
            // 此时获取的 OrderEvent 对象是一个没有被赋值的"空对象"
            OrderEvent orderEvent = ringBuffer.get(sequence);
            // 3. 实际的赋值处理
            orderEvent.setValue(data.getLong(0));
        } finally {
            // 4. 提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}
