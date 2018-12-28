package com.cheng;

import com.cheng.client.MessageConsumerImpl4Client;
import com.cheng.client.NettyClient;
import com.cheng.disruptor.MessageConsumer;
import com.cheng.disruptor.RingBufferWorkerPollFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);

        // 启动 disruptor
        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            consumers[i] = messageConsumer;
        }

        RingBufferWorkerPollFactory.getInstance()
                .initAndStart(ProducerType.MULTI,
                        1024 * 1024,
                        new BlockingWaitStrategy(),
//                        new YieldingWaitStrategy(),
                        consumers);

        // 建立连接并发送消息
        new NettyClient().sendData();
    }
}
