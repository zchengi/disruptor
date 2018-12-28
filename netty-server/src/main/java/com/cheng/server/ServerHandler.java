package com.cheng.server;

import com.cheng.disruptor.MessageProducer;
import com.cheng.disruptor.RingBufferWorkerPollFactory;
import com.cheng.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author cheng
 *         2018/12/27 23:20
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        /*TranslatorData request = (TranslatorData) msg;
        log.info("Server端: {}", request);

        // 数据库持久化操作 IO读写 ---> 交给一个线程池 异步调用执行
        TranslatorData response = new TranslatorData();
        response.setId("server: " + request.getId());
        response.setName("server: " + request.getName());
        response.setMessage("server: " + request.getMessage());

        // 写出 response 响应信息
        ctx.writeAndFlush(response);*/

        TranslatorData request = (TranslatorData) msg;

        // 自己的应用服务应该有一个 ID 生成规则
        String producerId = "code:sessionId:001";
        MessageProducer messageProducer = RingBufferWorkerPollFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(request, ctx);
    }
}
