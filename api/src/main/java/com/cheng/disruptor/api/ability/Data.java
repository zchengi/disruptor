package com.cheng.disruptor.api.ability;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cheng
 *         2018/12/22 16:30
 */
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data implements Serializable {

    private static final long serialVersionUID = -6688319299877324804L;

    private Long id;

    private String name;

}
