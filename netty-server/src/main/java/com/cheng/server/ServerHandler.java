package com.cheng.server;

import com.cheng.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/27 23:20
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        TranslatorData request = (TranslatorData) msg;
        log.info("Server端: {}", request);

        TranslatorData response = new TranslatorData();
        response.setId("server: " + request.getId());
        response.setName("server: " + request.getName());
        response.setMessage("server: " + request.getMessage());

        // 写出 response 响应信息
        ctx.writeAndFlush(response);
    }
}
