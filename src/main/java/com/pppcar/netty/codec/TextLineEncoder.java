package com.pppcar.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

/**
 * @author shining 编码
 */
public class TextLineEncoder extends StringEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, CharSequence msg,
			List<Object> out) throws Exception {
		super.encode(ctx, msg, out);
	}

}
