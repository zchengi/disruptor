package com.cheng.disruptor.api.ability;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 * Disruptor 性能测试
 *
 * @author cheng
 *         2018/12/22 17:02
 */
public class DisruptorSingle4Test {
    public static void main(String[] args) {

        int ringBufferSize = 65536;
        final Disruptor<Data> disruptor =
                new Disruptor<>(Data::new,
                        ringBufferSize,
                        // 单生产者单线程
                        Executors.newSingleThreadExecutor(),
//                        Executors.defaultThreadFactory(),
                        ProducerType.SINGLE,
                        // new BlockingWaitStrategy()
                        new YieldingWaitStrategy());

        DataConsumer consumer = new DataConsumer();
        // 消费数据
        disruptor.handleEventsWith(consumer);
        disruptor.start();

        // 投递数据
        new Thread(() -> {
            RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
            for (long i = 0; i < Constants.EVENT_NUM_OHM; i++) {
                long seq = ringBuffer.next();
                Data data = ringBuffer.get(seq);
                data.setId(i);
                data.setName("c" + i);
                ringBuffer.publish(seq);
            }
        }).start();
    }
}
