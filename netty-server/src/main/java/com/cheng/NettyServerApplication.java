package com.cheng;

import com.cheng.disruptor.MessageConsumer;
import com.cheng.disruptor.RingBufferWorkerPollFactory;
import com.cheng.server.MessageConsumerImpl4Server;
import com.cheng.server.NettyServer;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);

        // 启动 disruptor
        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverId:" + i);
            consumers[i] = messageConsumer;
        }

        RingBufferWorkerPollFactory.getInstance()
                .initAndStart(ProducerType.MULTI,
                        1024 * 1024,
                        new BlockingWaitStrategy(),
//                        new YieldingWaitStrategy(),
                        consumers);

        // 启动 netty
        new NettyServer();
    }
}
