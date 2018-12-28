package com.cheng.disruptor.api.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

/**
 * @author cheng
 *         2018/12/28 12:35
 */
public class KeyUtil {

    public static String generatorUUID() {
        TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        return timeBasedGenerator.generate().toString();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(KeyUtil.generatorUUID());

        System.out.println(KeyUtil.generatorUUID());
    }
}
