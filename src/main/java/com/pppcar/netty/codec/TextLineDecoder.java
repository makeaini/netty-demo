package com.pppcar.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.pppcar.netty.message.Message;


/**
 * @author shining
 *
 *         解码
 */
public class TextLineDecoder extends StringDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		String temp=msg.toString(Charset.forName("utf-8"));
		Message message = JSON.parseObject(temp, Message.class);
		out.add(message);
	}



	

}
