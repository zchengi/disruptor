package com.cheng.disruptor.api.high.multi;

import com.lmax.disruptor.RingBuffer;

/**
 * 生产者
 *
 * @author cheng
 *         2018/12/25 16:45
 */
public class Producer {

    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }


    public void sendData(String id) {

        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(id);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
