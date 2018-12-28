package com.cheng.entity;

import com.lmax.disruptor.ExceptionHandler;

/**
 * 异常处理
 *
 * @author cheng
 *         2018/12/25 15:52
 */
public class EventExceptionHandler implements ExceptionHandler<TranslatorDataWrapper> {

    @Override
    public void handleEventException(Throwable ex, long sequence, TranslatorDataWrapper event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
