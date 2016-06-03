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

public class ServerTextChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder",new TextLineDecoder());
		pipeline.addLast("encoder",new TextLineEncoder());
		pipeline.addLast("codec", new TextLineCodec());
		pipeline.addLast(new MessageTextHandler());
	}

}
