package com.cheng.client;

import com.cheng.disruptor.MessageProducer;
import com.cheng.disruptor.RingBufferWorkerPollFactory;
import com.cheng.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author cheng
 *         2018/12/28 0:00
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        /*try {
            TranslatorData response = (TranslatorData) msg;
            log.info("Client端: {}", response);
        } finally {
            // 一定要注意：用完了缓存要释放，便于GC
            ReferenceCountUtil.release(msg);
        }*/

        TranslatorData response = (TranslatorData) msg;
        String producerId = "code:sessionId:002";
        MessageProducer messageProducer = RingBufferWorkerPollFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);
    }
}
