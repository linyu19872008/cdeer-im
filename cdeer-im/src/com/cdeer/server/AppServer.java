package com.cdeer.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdeer.manager.ConstantManager;
import com.cdeer.server.codec.ProtobufDecoder;
import com.cdeer.server.codec.ProtobufEncoder;

/**
 * 客户端服务
 * 
 * @author jacklin
 * 
 */
public class AppServer {
	/**
	 * 生成日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;

	public AppServer(int workerNum) {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(workerNum);
	}

	public void startServer(int port) {
		// Configure the server.

		Log.info("正在启动Netty服务器,监听端口[" + port + "]");
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("HBeat", new IdleStateHandler(
						ConstantManager.HEART_BEAT + 10,
						ConstantManager.HEART_BEAT, 0));// 心跳
				pipeline.addLast("MsgDecoder", new ProtobufDecoder());// 解码器
				pipeline.addLast("MsgEncoder", new ProtobufEncoder());// 编码器
				pipeline.addLast("handler", new AppServerHandler());// 消息处理器

			}
		});

		applyConnectionOptions(bootstrap);

		InetSocketAddress addr = new InetSocketAddress(port);

		bootstrap.bind(addr).addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					Log.info("info:Netty服务器,启动成功");
				} else {
					Log.info("info:Netty服务器,启动失败");
				}
			}
		});

	}

	/**
	 * 停止服务器(一般用不到)
	 */
	public void shoutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();

	}

	/**
	 * 配置连接属性
	 * 
	 * @param bootstrap
	 * 
	 */
	protected void applyConnectionOptions(ServerBootstrap bootstrap) {

		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

		bootstrap.childOption(ChannelOption.SO_LINGER, 0);
		bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

	}
}
