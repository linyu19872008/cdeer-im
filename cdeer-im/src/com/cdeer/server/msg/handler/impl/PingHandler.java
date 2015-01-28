package com.cdeer.server.msg.handler.impl;

import io.netty.channel.Channel;

import com.cdeer.manager.AppRouterManager;
import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.server.msg.handler.AppMsgHandler;

/**
 * 心跳请求处理器
 * 
 * @author jacklin
 * 
 */
public class PingHandler implements AppMsgHandler {

	@Override
	public void process(Channel channel, Message msg2) {

		AppRouterManager.routePong(channel);
	}

}
