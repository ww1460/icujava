package com.pig4cloud.pigx.ccxxicu.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


	private ChannelHandlerContext ctx;

	/**
	 * 在到服务器的连接已经建立之后将被调用
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String rfidRequest = "{\"type\":\"N\",\"status\":0,\"data\":{\"ID\":1001,\"name\":\"\"}}";
		ctx.writeAndFlush(Unpooled.copiedBuffer(rfidRequest, CharsetUtil.UTF_8));//[P,0,[001,'xiaoli']],Netty rocks !
		this.ctx = ctx;
	}

	/**
	 * 当从服务器接收到一个消息时被调用
	 * @param channelHandlerContext
	 * @param byteBuf
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		System.out.println("Client received: "+ byteBuf.toString(CharsetUtil.UTF_8));
	}

	/**
	 * 在处理过程中引发异常时被调用
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public void sendMessage(Object message) {
		ctx.writeAndFlush(message);
	}

}
