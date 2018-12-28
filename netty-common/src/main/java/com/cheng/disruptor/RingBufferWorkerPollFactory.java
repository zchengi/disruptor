package com.cheng.disruptor;

import com.cheng.entity.EventExceptionHandler;
import com.cheng.entity.TranslatorDataWrapper;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * 环形缓冲工作池工厂
 *
 * @author cheng
 *         2018/12/28 11:03
 */
public class RingBufferWorkerPollFactory {

    private static Map<String, MessageProducer> producers = new ConcurrentHashMap<>(16);

    private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<>(16);

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    private SequenceBarrier sequenceBarrier;

    private WorkerPool<TranslatorDataWrapper> workerPool;

    private RingBufferWorkerPollFactory() {
    }

    private static class SingletonHolder {
        static final RingBufferWorkerPollFactory INSTANCE = new RingBufferWorkerPollFactory();
    }

    public static RingBufferWorkerPollFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {

        // 1. 构建 RingBuffer 对象
        ringBuffer =
                RingBuffer.create(
                        type,
                        TranslatorDataWrapper::new,
                        bufferSize,
                        waitStrategy);

        // 2. 设置序号栅栏（sequenceBarrier）
        this.sequenceBarrier = this.ringBuffer.newBarrier();

        // 3. 把所构建的消费者置入池中
        for (MessageConsumer consumer : messageConsumers) {
            consumers.put(consumer.getConsumerId(), consumer);
        }

        // 4. 设置消费者工作池
        this.workerPool = new WorkerPool<>(ringBuffer, sequenceBarrier, new EventExceptionHandler(), messageConsumers);

        // 5. 添加 sequences
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 6. 启动消费者工作池
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));
    }

    public MessageProducer getMessageProducer(String producerId) {
        MessageProducer messageProducer = producers.get(producerId);
        if (null == messageProducer) {
            messageProducer = new MessageProducer(producerId, ringBuffer);
            producers.put(producerId, messageProducer);
        }

        return messageProducer;
    }
}
