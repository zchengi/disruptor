package com.cheng.entity;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据传输对象包装类
 *
 * @author cheng
 *         2018/12/28 11:19
 */
@Getter
@Setter
public class TranslatorDataWrapper {

    private TranslatorData data;

    private ChannelHandlerContext ctx;
}
