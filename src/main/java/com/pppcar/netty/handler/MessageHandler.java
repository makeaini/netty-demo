package com.pppcar.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pppcar.netty.message.Message;

public class MessageHandler extends SimpleChannelInboundHandler<Message> {
/*	public static ChannelGroup channels = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);*/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageHandler.class);

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//这里可以设置一些参数
		AttributeKey<Integer> KEY_USER_ID = AttributeKey.newInstance("userId");  
		ctx.channel().attr(KEY_USER_ID).set(11443);
		int userId = ctx.channel().attr(KEY_USER_ID).get(); 
		System.out.println(userId);
/*		System.out.println(ctx.channel().localAddress().toString()
				+ " channelActive");*/
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message)
			throws Exception {
		System.out.println("server--MessageHandler:" + message);
		if (message.getMessageType().equals(2)) {
			Message message2 = new Message();
			message2.setId(UUID.randomUUID().toString());
			message2.setMessageType(2);
			message2.setContext("服务器回复普通消息");
			ctx.writeAndFlush(message2);
		}else if(message.getMessageType().equals(3)){
			ctx.channel().close();
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("-------------主动下线---------------");
	}
	


}
