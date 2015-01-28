package com.cdeer.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池管理器
 * 
 * @author jacklin
 * 
 */
public class RedisPoolManager {

	/**
	 * 日志输出类
	 */
	private static Logger Log = Logger.getLogger(RedisPoolManager.class.getName());
	
	/**
	 * 服务器地址
	 */
	public static String REDIS_SERVER = "localhost";

	/**
	 * 端口
	 */
	public static int REDIS_PORT = 6379;

	/**
	 * 密码
	 */
	public static String REDIS_AUTH = "2esaedas";

	private static JedisPool pool = null;

	private static JedisPool getInstance() {

		if (pool == null) {

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(500);
			config.setMaxIdle(20);
			config.setMaxWaitMillis(100 * 1000l);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);

			pool = new JedisPool(config, REDIS_SERVER, REDIS_PORT, 10000);
		}

		return pool;

	}

	/**
	 * 获取jedis
	 * 
	 * @return
	 */
	public static Jedis getJedis() {

		Jedis jedis = null;

		try {
			jedis = getInstance().getResource();
			// jedis.auth(REDIS_AUTH);
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}

		return jedis;

	}

	/**
	 * 返回jedis
	 * 
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis) {
		try {

			if (jedis != null) {
				getInstance().returnResource(jedis);
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}

	}

	/**
	 * 返回关闭的redis
	 * 
	 * @param jedis
	 */
	public static void returnBrokenJedis(Jedis jedis) {
		try {

			if (jedis != null) {
				getInstance().returnBrokenResource(jedis);
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}

	}
}
