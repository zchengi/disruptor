package com.cheng.server;

import com.cheng.codec.MarshallingCodeCFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/27 20:43
 */
@Slf4j
public class NettyServer {

    private static final int PORT = 8765;

    public NettyServer() {

        // 1. 创建两个工作线程组: 一个用于接收网络请求的线程组，另一个用于实际处理业务的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 2. 辅助类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 表示缓冲区动态调配(自适应)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    // 缓冲区池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    // 日志
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 异步回调过程，由 workerGroup 处理，不要在这里写业务逻辑代码，会阻塞 workerGroup，影响 Netty 性能
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ServerHandler());
                        }
                    });


            // 绑定端口，同步等待请求连接
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            log.info("Server Startup...");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅停机
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.info("Server Shutdown...");
        }
    }
}
