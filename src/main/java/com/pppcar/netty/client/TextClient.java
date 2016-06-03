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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pppcar.netty.codec.TextLineCodec;
import com.pppcar.netty.codec.TextLineDecoder;

public class TextClient {
	public static void main(String[] args) throws InterruptedException, IOException {
		EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
				pipeline.addLast("decoder",new TextLineDecoder());
				pipeline.addLast("encoder",new StringEncoder());
				pipeline.addLast("codec",new TextLineCodec());
				pipeline.addLast("handler", new MessageTextHandler());
			}
		});
    		b.option(ChannelOption.SO_KEEPALIVE, true);
    		b.option(ChannelOption.TCP_NODELAY, true);

            // 连接服务端
            ChannelFuture ch = b.connect("192.168.0.219", 8085).sync();
            ch.addListener(new ChannelFutureListener(){

				@Override
				public void operationComplete(ChannelFuture future)
						throws Exception {
					if(future.isSuccess()){
						System.out.println("连接成功");
					}
					
				}
            	
            	
            });
           Channel channel= ch.channel();
            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾 之所以用\r\n结尾 是因为我们在handler中添加了
                 * DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 */
                channel.writeAndFlush(line);
               /* {"id":"dfdf","messageType":2}*/
            }
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
	}
	

}
