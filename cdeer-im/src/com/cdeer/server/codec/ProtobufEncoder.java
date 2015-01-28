package com.cdeer.server.codec;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.server.AppAttrKeys;
import com.cdeer.utils.ThreeDES;

/**
 * 编码器
 * 
 * @author jacklin
 * 
 */
public class ProtobufEncoder extends MessageToByteEncoder<Message> {
	/**
	 * 日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)
			throws Exception {

		byte[] bytes = msg.toByteArray();// 将对象转换为byte

		// 加密消息体
		ThreeDES des = ctx.channel().attr(AppAttrKeys.ENCRYPT).get();
		byte[] encryptByte = des.encrypt(bytes);
		int length = encryptByte.length;// 读取消息的长度

		ByteBuf buf = Unpooled.buffer(2 + length);
		buf.writeShort(length);// 先将消息长度写入，也就是消息头
		buf.writeBytes(encryptByte);// 消息体中包含我们要发送的数据
		out.writeBytes(buf);

		Log.info("[APP-SERVER][SEND][remoteAddress:"
				+ ctx.channel().remoteAddress() + "][total length:" + length
				+ "][bare length:" + msg.getSerializedSize() + "]:\r\n"
				+ msg.toString());

	}

}
