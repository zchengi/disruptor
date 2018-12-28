package com.cheng.client;

import com.cheng.disruptor.MessageConsumer;
import com.cheng.entity.TranslatorData;
import com.cheng.entity.TranslatorDataWrapper;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/28 12:06
 */
@Slf4j
public class MessageConsumerImpl4Client extends MessageConsumer {

    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) {

        TranslatorData response = event.getData();
        try {
            log.info("Client端: {}", response);
        } finally {
            // 一定要注意：用完了缓存要释放，便于GC
            ReferenceCountUtil.release(response);
        }
    }
}
