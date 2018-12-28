package com.cheng.disruptor;

import com.cheng.entity.TranslatorData;
import com.cheng.entity.TranslatorDataWrapper;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author cheng
 *         2018/12/28 11:07
 */
public class MessageProducer {

    private String producerId;

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    public MessageProducer(String producerId, RingBuffer<TranslatorDataWrapper> ringBuffer) {
        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }

    public void onData(TranslatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWrapper wrapper = ringBuffer.get(sequence);
            wrapper.setData(data);
            wrapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
