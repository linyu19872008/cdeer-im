package com.cdeer.console;

import java.net.InetSocketAddress;

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
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdeer.manager.LogTAGManager;

/**
 * 控制台服务
 * 
 * @author jacklin
 * 
 */
public class ConsoleService {

	/**
	 * 生成日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;

	public ConsoleService(int workerNum) {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup(workerNum);
	}

	public void startServer(int port) {
		// Configure the server.

		Log.info(LogTAGManager.CONSOLE_INFO + "info:正在启动Console服务器,监听端口["
				+ port + "]");
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ChannelPipeline pipeline = ch.pipeline();

				pipeline.addLast("decoderLine",
						new LineBasedFrameDecoder(10240));// 解码器
				pipeline.addLast("decoderString", new StringDecoder());// 解码器

				pipeline.addLast("handler", new ConsoleServiceHandler());// 消息处理器

			}
		});

		applyConnectionOptions(bootstrap);

		InetSocketAddress addr = new InetSocketAddress(port);

		bootstrap.bind(addr).addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					Log.info(LogTAGManager.CONSOLE_INFO
							+ "info:Console服务器,启动成功");
				} else {
					Log.info(LogTAGManager.CONSOLE_INFO
							+ "info:Console服务器,启动失败");
				}
			}
		});

	}

	/**
	 * 关闭服务(基本不用)
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

	public static void main(String[] args) {

		// 配置日志
		PropertyConfigurator.configure("log4jtcp.properties");

		ConsoleService console = new ConsoleService(10);
		console.startServer(7777);
	}

}
