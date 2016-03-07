package com.eqcli.util;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Marshalling 编解码
 *
 */
public class MarshallingFactory {

	/**
	 * 创建Marshalling 编码器
	 * @return
	 */
	public static MarshallingDecoder buildMarshallingDecoder(){
		
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(1);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, config);
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
		return decoder;
	}
	
	/**
	 * 创建Marshalling 解码器
	 * @return
	 */
	public static MarshallingEncoder buildMarshallingEncoder(){
		
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(1);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, config);
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
}
