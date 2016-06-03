package com.pppcar.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.alibaba.fastjson.JSON;
import com.pppcar.netty.message.Message;

public class MessageTextHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Message message=(Message)msg;
		System.out.println(message.getId());
		message=new Message();
		message.setContext("收到了消息回复");
		message.setUserId("111");
		String sendMessage=JSON.toJSONString(message);
		/*String sendMessage="收到了消息回复";*/
		ctx.channel().writeAndFlush(sendMessage);
	}
	
	
}
