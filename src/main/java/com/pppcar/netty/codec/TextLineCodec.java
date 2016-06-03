package com.pppcar.netty.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class TextLineCodec extends MessageToMessageCodec<String,String>{
	@Override
	protected void encode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		out.add(msg + "\r\n");
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		out.add(msg);
	}



}
