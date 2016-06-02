package com.pppcar.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.pppcar.netty.codec.MessageDecoder;
import com.pppcar.netty.codec.MessageEncoder;
import com.pppcar.netty.message.Message;

public class Client {
	public static String HOST = "192.168.0.219";
	public static int PORT = 8085;
	public static Bootstrap bootstrap = getBootstrap();
	public static Channel channel = getChannel(HOST, PORT);

	/**
	 * 初始化Bootstrap
	 * 
	 * @return
	 */
	public static final Bootstrap getBootstrap() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				ClassResolver classResolver = new ClassResolver() {
					@Override
					public Class<?> resolve(String className)
							throws ClassNotFoundException {
						// TODO Auto-generated method stub
						return Message.class;
					}
				};
				pipeline.addLast("decoder", new MessageDecoder(classResolver));
				pipeline.addLast("encoder", new MessageEncoder());
				pipeline.addLast("ping", new IdleStateHandler(35, 20, 12));
				pipeline.addLast("handler", new ClientHandler());
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, true);
		b.option(ChannelOption.TCP_NODELAY, true);
		
		return b;

	}

	// 连接到服务端
	public static void doConnect() {
		ChannelFuture future = null;
		try {
			future = bootstrap.connect(new InetSocketAddress(HOST, PORT))
					.sync();
			channel=future.channel();
			future.addListener(new ConnectFutureListener());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("关闭连接");
		}

	}

	public static  Channel getChannel(String host, int port) {
		ChannelFuture channelFuture = null;
		try {
			channelFuture = bootstrap.connect(host, port).sync();
			channelFuture.addListener(new ConnectFutureListener());
			channelFuture.channel().closeFuture().addListener(new CloseFutureListener());
			channel = channelFuture.channel();
		} catch (Exception e) {
			return null;
		}
		return channel;
	}

	public static void sendUser(Message message) throws Exception {
		if (channel != null) {
			channel.writeAndFlush(message).sync();
		} else {
			System.out.print("消息发送失败,连接尚未建立!");
		}
	}

	
	
	
	public static void main(String[] args) throws Exception {
		  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			try {
          while(true){
        	  if(channel.isOpen()){
        	  String message=in.readLine().trim();
        	  Message sendMessage=null;
        	  	if(message.equals("2")){
        	  		sendMessage=new Message("2", "客户端发送的消息","100086",2);
        	  	}else if(message.equals("3")){
        	  		sendMessage=new Message("3",3);
        	  	}
        	    channel.writeAndFlush(sendMessage);
        	  }
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static class ConnectFutureListener implements ChannelFutureListener{

		@Override
		public void operationComplete(ChannelFuture f) throws Exception {
			if (f.isSuccess()) {
				System.out.println("连接服务器成功");
				//Message sendMessage=new Message("4","100085", 4);
				Message sendMessage=new Message("4","100086", 4);
				channel.writeAndFlush(sendMessage);
			} else {
				System.out.println("重新连接服务器失败");
				// 3秒后重新连接
				f.channel().eventLoop().schedule(new Runnable() {
					@Override
					public void run() {
						doConnect();
					}
				}, 3, TimeUnit.SECONDS);
			}
		}
		
	}
	private static  class CloseFutureListener implements ChannelFutureListener{

		@Override
		public void operationComplete(ChannelFuture f) throws Exception {
			System.out.println("关闭客户端listener运行了");
		}
		
	}
}

