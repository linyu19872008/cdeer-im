package com.cdeer.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdeer.manager.LogTAGManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 控制台服务处理器
 * 
 * @author jacklin
 * 
 */
public class ConsoleServiceHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 创建日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());

	/**
	 * 控制台消息处理器
	 */
	private ConsoleProcessor processor = new ConsoleProcessor();

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
			throws Exception {

		Log.info(LogTAGManager.CONSOLE_MSG_RECV + "length:"
				+ msg.toString().length() + LogTAGManager.LOG_SEPARATE_PARAMS
				+ "msg:" + msg);

		processor.handleMsg(ctx.channel(), (String) msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Log.info(LogTAGManager.CONSOLE_CONNECTED + "remoteAddress:"
				+ ctx.channel().remoteAddress());

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Log.info(LogTAGManager.CONSOLE_DISCONNECTED + "remoteAddress:"
				+ ctx.channel().remoteAddress());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
	}

}
