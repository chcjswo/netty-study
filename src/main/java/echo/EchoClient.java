package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * Created by chcjswo on 2019-08-04
 * <p>
 * Blog: http://mocadev.tistory.com
 * Github: http://github.com/chcjswo
 */
public class EchoClient {
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new EchoClientHandler());
						}
					});

			ChannelFuture f = b.connect("localhost", 8888).sync();

			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static class EchoClientHandler extends ChannelInboundHandlerAdapter {
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
		 * @param ctx ChannelHandlerContext
		 * @param msg Object
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
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
		 * @param ctx ChannelHandlerContext
		 */
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.close();
		}

		/**
		 * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
		 * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
		 * <p>
		 * Sub-classes may override this method to change behavior.
		 *
		 * @param ctx ChannelHandlerContext
		 * @param cause Throwable
		 */
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
		}
	}
}
