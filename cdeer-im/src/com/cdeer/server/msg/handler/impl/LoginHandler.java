package com.cdeer.server.msg.handler.impl;

import io.netty.channel.Channel;

import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.server.msg.handler.AppMsgHandler;

/**
 * 登录消息处理器
 * 
 * @author jacklin
 * 
 */
public class LoginHandler implements AppMsgHandler {

	@Override
	public void process(Channel channel, Message msg2) {

	}

}
