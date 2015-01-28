package com.cdeer.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cdeer.protobuf.CdeerMsg.Message;
import com.cdeer.server.AppAttrKeys;
import com.cdeer.utils.ThreeDES;

/**
 * 解码器
 * 
 * @author jacklin
 * 
 */
public class ProtobufDecoder extends ByteToMessageDecoder {
	/**
	 * 日志
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// 标记一下当前的readIndex的位置
		in.markReaderIndex();
		// 判断包头长度
		if (in.readableBytes() < 2) {// 不够包头
			return;
		}

		// 读取传送过来的消息的长度。
		int length = in.readUnsignedShort();

		// 长度如果小于0
		if (length < 0) {// 非法数据，关闭连接
			ctx.close();
		}

		if (length > in.readableBytes()) {// 读到的消息体长度如果小于传送过来的消息长度
			// 重置读取位置
			in.resetReaderIndex();
			return;
		}

		ByteBuf frame = Unpooled.buffer(length);
		in.readBytes(frame);

		try {
			byte[] inByte = frame.array();

			// 解密消息体
			ThreeDES des = ctx.channel().attr(AppAttrKeys.ENCRYPT)
					.get();
			byte[] bareByte = des.decrypt(inByte);

			// 字节转成对象
			Message msg = Message.parseFrom(bareByte);
			Log.info("[APP-SERVER][RECV][remoteAddress:"
					+ ctx.channel().remoteAddress() + "][total length:"
					+ length + "][bare length:" + msg.getSerializedSize()
					+ "]:\r\n" + msg.toString());

			if (msg != null) {
				// 获取业务消息头
				out.add(msg);
			}
		} catch (Exception e) {
			Log.info(ctx.channel().remoteAddress() + ",decode failed.", e);
		}

	}
}
