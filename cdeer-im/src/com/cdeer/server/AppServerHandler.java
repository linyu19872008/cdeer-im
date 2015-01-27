package com.cdeer.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdeer.manager.AppManager;
import com.cdeer.manager.AppRouterManager;
import com.cdeer.manager.LogTAGManager;
import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.server.msg.handler.AppMsgHandler;
import com.cdeer.utils.ThreeDES;

/**
 * 服务器处理器
 * 
 * @author jacklin
 * 
 */
public class AppServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 创建日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
			throws Exception {

		// message 解析
		Message receiveMsg = (Message) msg;
		// 相应的Handler
		AppMsgHandler msgHandler = AppMsgHandlerFactory
				.getAppMsgHandler(receiveMsg);
		if (msgHandler != null) {
			msgHandler.process(ctx.channel(), receiveMsg);
		} else {
			// 没找到相应的handler
			Log.error("no handler,msg2:" + receiveMsg.toString());
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {

		if (evt instanceof IdleStateEvent) {
			long userId = ctx.channel().attr(AppAttrKeys.USER_ID).get();
			// 验证连接可信度
			if (userId == 0l || AppManager.getConn(userId) == null) {// 连接不可信
				Log.error("remoteAddress:" + ctx.channel().remoteAddress());
				// 关闭客户端连接
				ctx.channel().close();
				return;
			}

			// 空闲事件(读或者写)
			IdleStateEvent event = (IdleStateEvent) evt;
			// Log.info("event:" + event.state() + "--" + event.isFirst());
			if (event.isFirst()) {
				// 如果是第一次触发,发送心跳包
				AppRouterManager.routePing(ctx.channel());

			} else {
				Log.info(LogTAGManager.CLIENT_IDLE_TIMEOUT + "userId:" + userId
						+ LogTAGManager.LOG_SEPARATE_PARAMS + "remoteAddress:"
						+ ctx.channel().remoteAddress());
				// 移除连接
				AppManager.removeConn(userId);
				ctx.channel().close();
			}

		} else {
			Log.error(LogTAGManager.CLIENT_UNKNOWN_EVENT + "event:"
					+ evt.getClass().toString());
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Log.info(LogTAGManager.CLIENT_CONNECTED + "remoteAddress:"
				+ ctx.channel().remoteAddress());
		// 添加加密方式
		ctx.channel().attr(AppAttrKeys.ENCRYPT).set(new ThreeDES());
		ctx.channel().attr(AppAttrKeys.USER_ID).set(0l);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		try {
			long userId = ctx.channel().attr(AppAttrKeys.USER_ID).get();
			Log.info(LogTAGManager.CLIENT_DISCONNECTED + "userId:" + userId
					+ LogTAGManager.LOG_SEPARATE_PARAMS + "remoteAddress:"
					+ ctx.channel().remoteAddress());
			if (0l != userId) {
				// 移除连接
				AppManager.removeConn(userId);
			}
			// 关闭连接
			if (ctx.channel().isOpen()) {
				ctx.channel().close();
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// super.exceptionCaught(ctx, cause);
	}

}
