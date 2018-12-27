package com.cheng.codec;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Marshalling工厂
 *
 * @author cheng
 *         2018/12/27 23:20
 */
public final class MarshallingCodeCFactory {

    /**
     * 创建 jboss Marshalling 解码器 MarshallingDecoder
     *
     * @return MarshallingDecoder
     */
    public static MarshallingDecoder buildMarshallingDecoder() {

        // 首先通过 Marshalling 工具类的精通方法获取 Marshalling 实例对象 参数 serial 标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        // 创建了 MarshallingConfiguration 对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        //根据 marshallerFactory 和 configuration 创建 provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        //构建 Netty 的 MarshallingDecoder 对象，俩个参数分别为 provider 和单个消息序列化后的最大长度
        return new MarshallingDecoder(provider, 1024 * 1024);
    }

    /**
     * 创建 jboss Marshalling 编码器 MarshallingEncoder
     *
     * @return MarshallingEncoder
     */
    public static MarshallingEncoder buildMarshallingEncoder() {

        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);

        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        // 构建 Netty 的 MarshallingEncoder 对象，MarshallingEncoder 用于实现序列化接口的POJO对象序列化为二进制数组
        return new MarshallingEncoder(provider);
    }
}
