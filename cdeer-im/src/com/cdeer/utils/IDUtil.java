package com.cdeer.utils;

import java.util.Date;
import java.util.Random;

/**
 * ID管理器
 * 
 * @author jacklin
 * 
 */
public class IDUtil {

	/**
	 * 生成包id
	 * 
	 * @return
	 */
	public static long generatePacketId() {

		long time = new Date().getTime();

		Random random = new Random();
		long ran = random.nextInt(1000);

		String str = time + "" + ran;

		return Long.valueOf(str);

	}

	/**
	 * 生成包id(短的)
	 * 
	 * @return
	 */
	public static long generatePacketIdShort() {

		Random random = new Random();
		long ran = random.nextInt(1000);

		return ran;

	}

}
