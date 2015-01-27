package com.cdeer.starter;

import org.apache.log4j.PropertyConfigurator;

import com.cdeer.manager.ConstantManager;
import com.cdeer.server.AppServer;
import com.cdeer.utils.Configuration;

/**
 * 服务器启动类
 * 
 * @author jacklin
 * 
 */
public class CdeerIMStarter {

	public static void main(String[] args) {
		// 配置日志
		PropertyConfigurator.configure("cdeer_log4j.properties");

		Configuration configuration = new Configuration(
				"cdeer_config.properties");
		ConstantManager.HEART_BEAT = Integer.valueOf(configuration
				.getValue("heartBeat"));
		ConstantManager.CONSOLE_PASS = configuration.getValue("consolePass");

		// 启动APP服务器
		String appServerPort = configuration.getValue("appServerPort");
		new AppServer(200).startServer(Integer.valueOf(appServerPort));

	}

}
