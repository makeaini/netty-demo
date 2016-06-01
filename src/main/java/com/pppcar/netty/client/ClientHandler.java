package com.pppcar.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pppcar.netty.message.Message;

public class ClientHandler extends SimpleChannelInboundHandler<Message> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClientHandler.class);


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		System.out.println("----client：" + msg);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		System.out.println("----服务器回复");
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.ALL_IDLE) {
				String uuid = UUID.randomUUID().toString();
				Message message = new Message(uuid, "客户端回复心跳消息", 1);
				ctx.writeAndFlush(message);
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("段开了连接");
		super.channelInactive(ctx);
		/*// 重新连接服务器
		ctx.channel().eventLoop().schedule(new Runnable() {
			@Override
			public void run() {
				Client.doConnect();
			}
		}, 2, TimeUnit.SECONDS);
		ctx.close();*/
	}

}
