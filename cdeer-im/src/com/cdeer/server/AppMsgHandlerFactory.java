package com.cdeer.server;

import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.server.msg.handler.AppMsgHandler;
import com.cdeer.server.msg.handler.impl.LoginHandler;
import com.cdeer.server.msg.handler.impl.PingHandler;
import com.cdeer.server.msg.handler.impl.PongHandler;

/**
 * 获取消息处理器
 * 
 * @author jackilin
 * 
 */
public class AppMsgHandlerFactory {

	public static AppMsgHandler getAppMsgHandler(Message msg) {

		int header = msg.getHeader();
		if (header == 101) {
			// 登录
			return new LoginHandler();
		} else if (header == 200) {
			// ping
			return new PingHandler();
		} else if (header == 201) {
			// pong
			return new PongHandler();
		} else {
			return null;
		}
	}

}
