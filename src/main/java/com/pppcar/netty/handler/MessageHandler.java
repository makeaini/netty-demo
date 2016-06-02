package com.pppcar.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pppcar.netty.constant.Constants;
import com.pppcar.netty.manager.TcpChannelsManager;
import com.pppcar.netty.message.Message;

public class MessageHandler extends SimpleChannelInboundHandler<Message> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageHandler.class);
	

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//这里可以设置一些参数
		/*AttributeKey<Integer> KEY_USER_ID = AttributeKey.newInstance("userId");  
		ctx.channel().attr(KEY_USER_ID).set(11443);
		int userId = ctx.channel().attr(KEY_USER_ID).get(); 
		System.out.println(ctx.channel().id());*/
		/*System.out.println(userId);*/
/*		System.out.println(ctx.channel().localAddress().toString()
				+ " channelActive");*/
		TcpChannelsManager.getInstance().addUnChannel(ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message message)
			throws Exception {
		AttributeKey<String> KEY_USER_ID =null;
		if(!AttributeKey.exists(Constants.KEY_USER_ID)){
			KEY_USER_ID= AttributeKey.newInstance(Constants.KEY_USER_ID);
		}else{
			KEY_USER_ID=AttributeKey.valueOf(Constants.KEY_USER_ID);
		}
		Channel channel=ctx.channel();
		String sendUser=channel.attr(KEY_USER_ID).get();
		if(!message.getMessageType().equals(4)){
			System.out.println("server--MessageHandler:" + message);
			if (sendUser == null) {
				LOGGER.error("对不起没有认证");
				ctx.channel().close();
				return;
			}
		}
		if (message.getMessageType().equals(2)) {
			Channel channel2=TcpChannelsManager.getInstance().getChannel(message.getTo());
			String toUser=channel2.attr(KEY_USER_ID).get();
			channel2.writeAndFlush(message);
			LOGGER.info(sendUser+"发送消息给:"+toUser);
		}else if(message.getMessageType().equals(3)){
			ctx.channel().close();
		}else if(message.getMessageType().equals(4)){
			channel.attr(KEY_USER_ID).set(message.getUserId());
			TcpChannelsManager.getInstance().addChannel(channel);
			TcpChannelsManager.getInstance().removeUnChannel(channel);
			Message message2=new Message();
			message2.setContext("认证成功");
			message2.setId("4");
			message2.setMessageType(4);
			channel.writeAndFlush(message2);
			int size=TcpChannelsManager.getInstance().getAuthchannelSize();
			LOGGER.info("认证的size:{}",size);
			
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		LOGGER.error("-------------主动下线---------------");
		TcpChannelsManager.getInstance().removeUnChannel(ctx.channel());
		LOGGER.error("---当前未认证的条数："+TcpChannelsManager.getInstance().getUnchannelSize());
	}
	


}
