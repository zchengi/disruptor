package com.cheng.disruptor.api.high.multi;

import com.lmax.disruptor.ExceptionHandler;

/**
 * 异常处理
 *
 * @author cheng
 *         2018/12/25 15:52
 */
public class EventExceptionHandler implements ExceptionHandler<Order> {

    @Override
    public void handleEventException(Throwable ex, long sequence, Order event) {

    }

    @Override
    public void handleOnStartException(Throwable ex) {

    }

    @Override
    public void handleOnShutdownException(Throwable ex) {

    }
}
