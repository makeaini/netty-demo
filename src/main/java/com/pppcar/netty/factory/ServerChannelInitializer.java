package com.pppcar.netty.factory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.timeout.IdleStateHandler;

import com.pppcar.netty.codec.MessageDecoder;
import com.pppcar.netty.codec.MessageEncoder;
import com.pppcar.netty.handler.KeepHeartHandler;
import com.pppcar.netty.handler.MessageHandler;
import com.pppcar.netty.message.Message;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
/*	这里readerIdleTime为读超时时间（即服务器一定时间内未接受到客户端消息）
	writerIdleTime为写超时时间（即服务器一定时间内向客户端发送消息）
	allIdleTime为全体超时时间（即同时没有读写的时间）*/
	
	private static final int READ_WAIT_SECONDS = 15;
	private static final int WIRTE_WAIT_SECONDS = 15;
	private static final int TIME_OUT_SECONDS = 30;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		ClassResolver classResolver = new ClassResolver() {
			@Override
			public Class<?> resolve(String className)
					throws ClassNotFoundException {
				return Message.class;
			}
		};
		pipeline.addLast("decoder", new MessageDecoder(classResolver));
		pipeline.addLast("encoder", new MessageEncoder());
		pipeline.addLast("ping", new IdleStateHandler(READ_WAIT_SECONDS, WIRTE_WAIT_SECONDS, TIME_OUT_SECONDS)); 
		pipeline.addLast(new KeepHeartHandler());
		pipeline.addLast(new MessageHandler());
	}

}
