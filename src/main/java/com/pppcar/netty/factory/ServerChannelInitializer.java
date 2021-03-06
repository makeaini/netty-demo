package com.pppcar.netty.factory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import com.pppcar.netty.codec.TextLineCodec;
import com.pppcar.netty.codec.MessageDecoder;
import com.pppcar.netty.codec.MessageEncoder;
import com.pppcar.netty.codec.TextLineDecoder;
import com.pppcar.netty.codec.TextLineEncoder;
import com.pppcar.netty.handler.KeepHeartHandler;
import com.pppcar.netty.handler.MessageHandler;
import com.pppcar.netty.handler.MessageTextHandler;
import com.pppcar.netty.message.Message;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
/*	这里readerIdleTime为读超时时间（即服务器一定时间内未接受到客户端消息）
	writerIdleTime为写超时时间（即服务器一定时间内向客户端发送消息）
	allIdleTime为全体超时时间（即同时没有读写的时间）*/
	
	private static final int READ_WAIT_SECONDS = 30;
	private static final int WIRTE_WAIT_SECONDS = 15;
	private static final int TIME_OUT_SECONDS = 30;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(Message.class.getClassLoader())));
		pipeline.addLast("encoder", new ObjectEncoder());
/*		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder",new TextLineDecoder());
		pipeline.addLast("encoder",new TextLineEncoder());
		pipeline.addLast("codec", new TextLineCodec());  */
		pipeline.addLast("ping", new IdleStateHandler(READ_WAIT_SECONDS, WIRTE_WAIT_SECONDS, TIME_OUT_SECONDS));
		pipeline.addLast(new KeepHeartHandler());
		pipeline.addLast(new MessageHandler());
	}

}
