package com.cdeer.console;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cdeer.manager.ConstantManager;
import com.cdeer.manager.LogTAGManager;
import com.cdeer.redis.RedisPoolManager;

import redis.clients.jedis.Jedis;

/**
 * 控制台消息处理器
 * 
 * @author jacklin
 * 
 */
public class ConsoleProcessor {

	/**
	 * 日志输出类
	 */
	private Logger Log = Logger.getLogger(getClass());

	/**
	 * 命令集合
	 */
	private List<String> commandList = new ArrayList<String>();

	/**
	 * 前一条命令
	 */
	private String preMsg = "";

	public ConsoleProcessor() {

		commandList.add("[login <password>]: Login Console");
		commandList.add("[show]:   查看命令列表");
		commandList.add("[redis]:  查看redis状况");
		commandList.add("[bye]: Good Bye");

	}

	/**
	 * 处理控制台消息
	 * 
	 * @return
	 */
	public void handleMsg(Channel channel, String msg) {
		try {
			if ("pre".equals(msg)) {
				msg = preMsg;
			}
			preMsg = msg;

			String[] msgArr = msg.split(" ");
			String command = msgArr[0];

			if ("login".equals(command)) {
				// 控制台登录
				if (msgArr.length > 1) {
					String password = msgArr[1];
					if (ConstantManager.CONSOLE_PASS.equals(password)) {
						// 登录成功
						channel.attr(ConsoleAttrKeys.LOGIN).set(1);
						sendMsg(channel, "login success");
					} else {
						channel.close();
					}
					return;
				} else {
					// 长度不够
					channel.close();
					return;
				}
			}

			// 验证用户权限
			Attribute<Integer> login = channel.attr(ConsoleAttrKeys.LOGIN);
			if (login == null || login.get() == 0) {

				Log.error("错误内容:" + msg + "[客户端信息:" + channel.remoteAddress()
						+ "]");
				sendMsg(channel, "I do not love you ..");
				channel.close();
				return;
			}

			if ("redis".equals(command)) {
				// redis服务器信息
				sendMsg(channel, getRedisInfo());
			} else if ("show".equals(command)) {
				// 控制台命令列表
				sendMsg(channel, getCommandList());
			} else if ("bye".equals(command)) {
				// 关闭连接
				sendMsg(channel, "Bye-Bye...");
				channel.close();
			} else {
				// 错误命令
				sendMsg(channel, "command error,please try again...");
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
			Log.error("[错误消息内容]:[" + msg + "],[客户端信息]:"
					+ channel.remoteAddress().toString());
			sendMsg(channel, "I do not love you...");
			channel.close();
		}

	}

	/**
	 * 获取redis信息
	 * 
	 * @return
	 */
	public String getRedisInfo() {

		String redisInfo = "";
		Jedis jedis = null;
		try {
			jedis = RedisPoolManager.getJedis();

			redisInfo = jedis.info() + "\r\n";

		} catch (Exception e) {
			Log.error(e.getMessage(), e);
			RedisPoolManager.returnBrokenJedis(jedis);
		} finally {
			RedisPoolManager.returnJedis(jedis);
		}

		return redisInfo;
	}

	/**
	 * 命令列表
	 * 
	 * @return
	 */
	public String getCommandList() {

		StringBuffer sbuff = new StringBuffer();
		sbuff.append("IM Server Command List=========================\r\n");
		int i = 0;
		for (String command : commandList) {
			i++;
			sbuff.append("[" + i + "]" + command + "\r\n");
		}
		sbuff.append("IM Server Command List=========================\r\n");
		return sbuff.toString();

	}

	/**
	 * 发送消息
	 * 
	 * @param channel
	 * @param msg
	 */
	private void sendMsg(Channel channel, String msg) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNowStr = "当前时间:  " + formatter.format(new Date()) + "\r\n";
		msg = timeNowStr + msg;
		Log.info(LogTAGManager.CONSOLE_MSG_SEND + "length:"
				+ msg.toString().length() + LogTAGManager.LOG_SEPARATE_PARAMS
				+ "msg:" + msg);

		String sendStr = msg + "\r\n";
		ByteBuf echo = Unpooled.copiedBuffer(sendStr.getBytes());
		channel.writeAndFlush(echo);
	}
}
