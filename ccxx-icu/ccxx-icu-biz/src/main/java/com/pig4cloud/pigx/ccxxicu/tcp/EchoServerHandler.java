package com.pig4cloud.pigx.ccxxicu.tcp;

import cn.hutool.json.JSONObject;
import com.alibaba.nacos.api.config.filter.IFilterConfig;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private static String RFID_ACCEPT_EXCEPTION = "5001";

	private static String RFID_RESPONSE_EXCEPTION = "5002";

	private static String RFID_REQUEST_EXCEPTION = "5003";

	private static String RFID_ERROR_EXCEPTION = "5004";

	private static String FRID_CLASS_CASE_EXCEPTION = "5005";

	private static String FRID_RESOLVING_EXCEPTION = "5006";//解析异常

	private static String FRID_NODEFIN_EXCEPTION = "5007";//未定义类型

	private static final String N = "N";

	private static final String P = "P";

	private static final String E = "E";

	private static final String M = "M";

	private static final String B = "B";

	private static final String C = "C";


	/**
	 * 对每一个传入的消息都要调用；
	 * 注意：按照场景进行分类处理：病房，配药室，垃圾收集处
	 *
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf in = (ByteBuf) msg;
		if (in == null) {
			throw new Exception(RFID_ACCEPT_EXCEPTION, new Throwable("接收信息为空"));
		}

		String res = in.toString(CharsetUtil.UTF_8);
		System.out.println("server received: " + res);
		if (StringUtils.isEmpty(res)) {
			throw new Exception(FRID_CLASS_CASE_EXCEPTION, new Throwable("ByteBuf 类型转换异常"));
		}

		JSONObject jsonObject = new JSONObject(res);
		String action = (String) jsonObject.get("type");
		String medication = (String) jsonObject.get("action");
		log.info("Json 解析client：" + action);
		if (StringUtils.isEmpty(action)) {
			throw new Exception(FRID_RESOLVING_EXCEPTION, new Throwable("数据类型解析异常"));
		}
		switch (medication) {
			case N://护士
				System.out.println("222");
				break;
			case P://病人
				break;
			case E://设备
				break;
			case M://药品
				break;
			case B://床
				break;
			case C://卡片电脑
				break;

			default:
				//throw new Exception(FRID_NODEFIN_EXCEPTION, new Throwable("未知类型"));
				System.out.println("未知类型异常,code值:" + FRID_NODEFIN_EXCEPTION);
				break;
		}

		ctx.write(in);//in
	}


	/**
	 * 通知ChannelInboundHandler最后一次对channelRead()的调用时当前批量读取中的的最后一条消息。
	 *
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * 在读取操作期间，有异常抛出时会调用。
	 *
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
