package com.cheng.disruptor.api.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 4. 实例化 Disruptor 实例，配置一系列参数，编写 Disruptor 核心组件
 *
 * @author cheng
 *         2018/12/22 18:43
 */
public class Main {
    public static void main(String[] args) {

        // 参数准备工作
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 1024 * 1024;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        /*
         * 参数：
         * 1.eventFactory:   消息(event)工厂对象容器长度
         * 2.ringBufferSize: 容器的长度
         * 3.threadFactory:  线程池创建工厂(建议自定义线程池)
         * 4.producerType:   单生产者还是多生产者
         * 5.waitStrategy:   等待策略
         */
        // (1). 实例化 Disruptor 对象
        Disruptor<OrderEvent> disruptor =
                new Disruptor<>(orderEventFactory,
                        ringBufferSize,
                        threadFactory,
                        // 消费者类型
                        ProducerType.SINGLE,
                        // 等待策略
                        new BlockingWaitStrategy());

        // (2). 添加消费者的监听（构建 disruptor 与消费者的一个关联关系）
        disruptor.handleEventsWith(new OrderEventHandler());

        // (3). 启动 disruptor
        disruptor.start();

        // (4). 获取实际存储数据的容器： RingBuffer(环行结构)
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        // (5). 生产者生产数据
        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 0; i < 100; i++) {
            byteBuffer.putLong(0, i);
            producer.sendData(byteBuffer);
        }

//        disruptor.shutdown();
    }
}
