package com.cheng.server;

import com.cheng.disruptor.MessageConsumer;
import com.cheng.entity.TranslatorData;
import com.cheng.entity.TranslatorDataWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cheng
 *         2018/12/28 12:00
 */
@Slf4j
public class MessageConsumerImpl4Server extends MessageConsumer {

    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) {

        // 业务逻辑处理
        TranslatorData request = event.getData();
        log.info("Server端: {}", request);

        TranslatorData response = new TranslatorData();
        response.setId("server: " + request.getId());
        response.setName("server: " + request.getName());
        response.setMessage("server: " + request.getMessage());

        event.getCtx().writeAndFlush(response);
    }
}
