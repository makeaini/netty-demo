package com.pppcar.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.pppcar.netty.message.Message;

public class MessageTextHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		/*Message message=(Message)msg;*/
		Message message=(Message)msg;
		System.out.println(message.getContext());
	}
	
	
}
