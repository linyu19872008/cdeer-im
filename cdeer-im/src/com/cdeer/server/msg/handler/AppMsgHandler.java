package com.cdeer.server.msg.handler;

import io.netty.channel.Channel;

import com.cdeer.protobuf.CdeerMsg.Message;

/**
 * 消息处理器
 * 
 * @author jacklin
 * 
 */
public interface AppMsgHandler {

	/**
	 * 处理方法
	 * 
	 * @param channel
	 * @param msg2
	 */
	public void process(Channel channel, Message msg2);

}