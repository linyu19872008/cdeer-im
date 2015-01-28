package com.cdeer.manager;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdeer.protobuf.CdeerMsg.ErrorInfo;
import com.cdeer.protobuf.CdeerMsg.Message;

/**
 * app消息发送管理器
 * 
 * @author jacklin
 * 
 */
public class AppRouterManager {

	/**
	 * 生成日志对象
	 */
	private final static Logger Log = LoggerFactory
			.getLogger(AppRouterManager.class);

	/**
	 * 发送错误
	 * 
	 * @param channel
	 * @param code
	 * @param info
	 * @param expose
	 * @param userId
	 */
	public static void routeError(Channel channel, int code, String info,
			int expose, long userId) {

		ErrorInfo.Builder error = ErrorInfo.newBuilder();
		error.setCode(code);
		error.setInfo(info);
		error.setExpose(expose);

		Message.Builder msg = Message.newBuilder();
		msg.setHeader(404);
		msg.setErrorInfo(error);
		Message msgSend = msg.build();

		Log.info(LogTAGManager.CLIENT_ERROR + "userId:" + userId
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "code:" + code
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "info:" + info
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "expose:" + expose
				+ LogTAGManager.LOG_SEPARATE_PARAMS + "remoteAddress:"
				+ channel.remoteAddress().toString());

		routeDerict(channel, msgSend);
	}

	/**
	 * 发送心跳请求
	 * 
	 * @param channel
	 */
	public static void routePing(Channel channel) {

		Message.Builder msg = Message.newBuilder();
		msg.setHeader(200);

		Message msgSend = msg.build();

		routeDerict(channel, msgSend);
	}

	/**
	 * 发送心跳响应
	 * 
	 * @param channel
	 */
	public static void routePong(Channel channel) {

		Message.Builder msg = Message.newBuilder();
		msg.setHeader(201);

		Message msgSend = msg.build();

		routeDerict(channel, msgSend);
	}

	/**
	 * 直接发送消息
	 * 
	 * @param channel
	 *            通道
	 * @param msgSend
	 *            要发送的消息
	 */
	public static void routeDerict(Channel channel, Message msgSend) {

		try {
			channel.writeAndFlush(msgSend);
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}
	}

}
