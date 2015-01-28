package com.cdeer.server.msg.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

import com.cdeer.manager.AppRouterManager;
import com.cdeer.manager.LogTAGManager;
import com.cdeer.protobuf.CdeerMsg.LoginInfo;
import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.redis.RedisDBProvider;
import com.cdeer.server.msg.handler.AppMsgHandler;

/**
 * 登录消息处理器
 * 
 * @author jacklin
 * 
 */
public class LoginHandler implements AppMsgHandler {

	/**
	 * 创建日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(Channel channel, Message msg2) {

		LoginInfo loginInfo = msg2.getLoginInfo();
		long userId = loginInfo.getUserId();
		String token = loginInfo.getToken();
		String platform = loginInfo.getPlatform().toLowerCase();
		String appVersion = loginInfo.getAppVersion();

		Log.info(LogTAGManager.CLIENT_LOGIN_INFO + "userId:" + userId
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "token:" + token
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "platform:" + platform
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "appversion:"
				+ appVersion);

		// token验证
		String user_token = RedisDBProvider.getUserInfo(userId, "user_token");
		if ((user_token == null || "".equals(user_token) || (!user_token
				.equals(token)))) {
			int code = 1;
			String info = "登录验证失败";
			int expose = 1;
			AppRouterManager.routeError(channel, code, info, expose, userId);
			channel.close();
			return;
		}

	}

}
