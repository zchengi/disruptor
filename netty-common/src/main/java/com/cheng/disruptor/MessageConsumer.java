package com.cheng.disruptor;

import com.cheng.entity.TranslatorDataWrapper;
import com.lmax.disruptor.WorkHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * @author cheng
 *         2018/12/28 11:07
 */
@Getter
@Setter
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWrapper> {

    protected String consumerId;

    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }
}
