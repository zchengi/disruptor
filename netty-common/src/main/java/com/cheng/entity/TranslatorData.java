package com.cheng.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据传输对象
 * @author cheng
 *         2018/12/27 20:55
 */
@Data
public class TranslatorData implements Serializable {

    private static final long serialVersionUID = 1821294775977343978L;

    private String id;
    private String name;
    /**
     * 传输消息体内容
     */
    private String message;
}
