package com.pppcar.netty.codec;

import com.alibaba.fastjson.JSON;
import com.pppcar.netty.message.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;

/**
 * @author shining
 *
 *         解码
 */
public class MessageDecoder extends ObjectDecoder {

	public MessageDecoder(ClassResolver classResolver) {
		super(classResolver);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		String frame = (String) super.decode(ctx, in);
		if (frame == null) {
			return null;
		}
		Message message=JSON.parseObject(frame,Message.class);	
		return message;
	}

}
