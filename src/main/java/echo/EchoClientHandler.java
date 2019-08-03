package echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.nio.charset.Charset;

/**
 * Created by chcjswo on 2019-08-04
 * Blog: http://mocadev.tistory.com
 * Github: http://github.com/chcjswo
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		String sendMessage = "Hello, Netty!";

		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(sendMessage.getBytes());

		StringBuilder builder = new StringBuilder();
		builder.append("전송한 문자열: [").append(sendMessage).append("]");

		System.out.println(builder.toString());

		ctx.writeAndFlush(messageBuffer);
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
	 * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
	 * <p>
	 * Sub-classes may override this method to change behavior.
	 *
	 * @param ctx
	 * @param msg
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());

		StringBuilder builder = new StringBuilder();
		builder.append("수신한 문자열: [").append(readMessage).append("]");

		System.out.println(builder.toString());
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelReadComplete()} to forward
	 * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
	 * <p>
	 * Sub-classes may override this method to change behavior.
	 *
	 * @param ctx
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
	 * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
	 * <p>
	 * Sub-classes may override this method to change behavior.
	 *
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
