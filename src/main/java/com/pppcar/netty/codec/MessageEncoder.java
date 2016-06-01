package com.pppcar.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.pppcar.netty.message.Message;

/**
 * @author shining
 * 编码
 */
public class MessageEncoder extends ObjectEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, Serializable msg,
			ByteBuf out) throws Exception {
		Message message=(Message)msg;
		String jsonMessage=JSON.toJSONString(message);
		super.encode(ctx, jsonMessage, out);
	}






}
