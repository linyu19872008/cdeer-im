package com.cdeer.manager;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * APP连接管理器
 * 
 * @author jacklin
 * 
 */
public class AppManager {

	/**
	 * 存放所有客户端登陆连接
	 */
	private static Map<Long, Channel> appConnMap = new ConcurrentHashMap<Long, Channel>();

	/**
	 * 添加连接
	 * 
	 * @param userId
	 * @param channel
	 */
	public static void addConn(Long userId, Channel channel) {
		appConnMap.put(userId, channel);
	}

	/**
	 * 移除连接
	 * 
	 * @param userId
	 */
	public static void removeConn(Long userId) {

		if (appConnMap.containsKey(userId)) {
			appConnMap.remove(userId);
		}
	}

	/**
	 * 获取用户Channel
	 * 
	 * @param userId
	 * @return
	 */
	public static Channel getConn(Long userId) {

		return appConnMap.get(userId);
	}

}
