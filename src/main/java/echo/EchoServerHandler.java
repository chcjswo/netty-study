package echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by chcjswo on 2019-08-03
 *
 * Blog: http://mocadev.tistory.com
 * Github: http://github.com/chcjswo
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());

		System.out.println("수신한 문자열: [" + readMessage + "]");

		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}
}
