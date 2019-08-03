package discard;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by chcjswo on 2019-08-03
 *
 * Blog: http://mocadev.tistory.com
 * Github: http://github.com/chcjswo
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * <strong>Please keep in mind that this method will be renamed to
	 * {@code messageReceived(ChannelHandlerContext, I)} in 5.0.</strong>
	 * <p>
	 * Is called for each message of type {@link I}.
	 *
	 * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
	 *            belongs to
	 * @param msg the message to handle
	 * @throws Exception is thrown if an error occurred
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

	}
}
