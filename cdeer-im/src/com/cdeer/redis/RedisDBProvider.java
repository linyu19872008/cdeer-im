package com.cdeer.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * Redis数据管理器
 * 
 * @author jacklin
 * 
 */
public class RedisDBProvider {

	private static final Logger Log = LoggerFactory
			.getLogger(RedisDBProvider.class);

	/**
	 * 设置用户信息
	 * 
	 * @param userId
	 * @param field
	 * @return
	 */
	public static void setUserInfo(long userId, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisPoolManager.getJedis();
			// 用户表
			jedis.select(RedisConstantManager.REDIS_DB_USER);
			jedis.hset("user_" + userId, field, value);
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
			RedisPoolManager.returnBrokenJedis(jedis);
		} finally {
			RedisPoolManager.returnJedis(jedis);
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 * @param field
	 * @return
	 */
	public static String getUserInfo(long userId, String field) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = RedisPoolManager.getJedis();
			// 用户表
			jedis.select(RedisConstantManager.REDIS_DB_USER);
			result = jedis.hget("user_" + userId, field);
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
			RedisPoolManager.returnBrokenJedis(jedis);
		} finally {
			RedisPoolManager.returnJedis(jedis);
		}
		return result;
	}

}
