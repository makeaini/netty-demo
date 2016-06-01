package com.pppcar.netty.handler;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import com.pppcar.netty.message.Message;

public class KeepHeartHandler extends SimpleChannelInboundHandler<Message> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(KeepHeartHandler.class);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		if(msg.getMessageType().equals(1)){
			System.out.println("server--KeepHeartHandler:" + msg);
			return;
		}
		//传递到下一个handler
		ctx.fireChannelRead(msg);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {

			IdleStateEvent event = (IdleStateEvent) evt;

			if (event.state().equals(IdleState.READER_IDLE)) {
				// 未进行读操作
				LOGGER.info("READER_IDLE");
				// 超时关闭channel
				ctx.channel().close();

			} else if (event.state().equals(IdleState.WRITER_IDLE)) {

			} else if (event.state().equals(IdleState.ALL_IDLE)) {
				// 未进行读写
				LOGGER.info("ALL_IDLE");
				// 发送心跳消息
				String id = UUID.randomUUID().toString();
				Message message = new Message(id, "服务器向客户端发送心跳消息", 1);
				ctx.writeAndFlush(message);
			}
		}else{
			super.userEventTriggered(ctx, evt);
		}
	
	}

}
