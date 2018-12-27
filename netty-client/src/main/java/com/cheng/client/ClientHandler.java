package com.cheng.client;

import com.cheng.entity.TranslatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/28 0:00
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            TranslatorData response = (TranslatorData) msg;
            log.info("Client端: {}", response);
        } finally {
            // 一定要注意：用完了缓存要释放，便于GC
            ReferenceCountUtil.release(msg);
        }
    }
}
