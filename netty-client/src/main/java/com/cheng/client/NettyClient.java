package com.cheng.client;

import com.cheng.codec.MarshallingCodeCFactory;
import com.cheng.entity.TranslatorData;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/27 20:43
 */
@Slf4j
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8765;

    /**
     * todo 完善池化: ConcurrentHashMap<KEY -> String, Value -> Channel>
     */
    private Channel channel;

    /**
     * 1. 创建一个工作线程组: 用于实际处理业务的线程组
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public NettyClient() {
        this.connect(HOST, PORT);
    }

    private void connect(String host, int port) {

        // 2. 辅助类
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    // 表示缓冲区动态调配(自适应)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    // 缓冲区池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    // 日志
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 异步回调过程，由 workerGroup 处理，不要在这里写业务逻辑代码，会阻塞 workerGroup，影响 Netty 性能
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ClientHandler());
                        }
                    });

            // 绑定端口，同步等待请求连接
            ChannelFuture channelFuture = bootstrap.connect(host, PORT).sync();
            log.info("Client Connected...");

            // 数据发送
            this.channel = channelFuture.channel();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendData() {

        for (int i = 0; i < 10; i++) {
            TranslatorData request = new TranslatorData();
            request.setId("" + i);
            request.setName("请求消息名称" + i);
            request.setMessage("请求消息内容" + i);

            channel.writeAndFlush(request);
        }
    }

    public void close() throws InterruptedException {

        channel.closeFuture().sync();

        // 优雅停机
        workerGroup.shutdownGracefully();
        log.info("Client Shutdown...");
    }
}
