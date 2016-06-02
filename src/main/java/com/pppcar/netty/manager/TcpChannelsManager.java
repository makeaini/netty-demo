package com.pppcar.netty.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pppcar.netty.constant.Constants;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

public class TcpChannelsManager {
	private ChannelGroup unauthChannels = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);
	private Map<String, Channel> authChanels = new ConcurrentHashMap<String, Channel>();

	private static TcpChannelsManager instance;

	private TcpChannelsManager() {

	}

	public static TcpChannelsManager getInstance() {
		if (instance == null) {
			synchronized (TcpChannelsManager.class) {
				instance = new TcpChannelsManager();
			}
		}
		return instance;
	}
	/**
	 * 添加一个新的未认证会话
	 * 
	 * @param session
	 */
	public void addUnChannel(Channel channel) {
		unauthChannels.add(channel);
	}
	/**
	 * 移除一个未认证的会话
	 * @param channel
	 */
	public void removeUnChannel(Channel channel) {
		unauthChannels.remove(channel);
	}
	
	
	public int getUnchannelSize(){
		return unauthChannels.size();
	}
	
	
	public Channel  getChannel(String userId){
		return authChanels.get(userId);
	}
	/**
	 * 添加一个认证的会话
	 */
	public void addChannel(Channel channel){
		AttributeKey<String> KEY_USER_ID = AttributeKey.valueOf(Constants.KEY_USER_ID);  
		String userId = channel.attr(KEY_USER_ID).get(); 
		authChanels.put(userId, channel);
		
	}
	public int getAuthchannelSize(){
		return authChanels.size();
	}
	
}
