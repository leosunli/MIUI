package attributeMap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by leo on 17-2-4.
 */
public class HelloWorldClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");

    static final int PORT = Integer.parseInt(System.getProperty("port", "8000"));

    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        initChannel();
    }

    private static void initChannel() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    //在TCP/IP协议中，无论发送多少数据，总是要在数据前面加上协议头，同时，对方接收到数据，
                    // 也需要发送ACK表示确认。为了尽可能的利用网络带宽，TCP总是希望尽可能的发送足够大的数据。
                    // 这里就涉及到一个名为Nagle的算法，该算法的目的就是为了尽可能发送大块数据，
                    // 避免网络中充斥着许多小数据块

                    //ChannelOption.TCP_NODELAY TCP_NODELAY就是用于启用或关于Nagle算法。
                    // 如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；
                    // 如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encode", new StringEncoder());
                            p.addLast(new HelloWorldClientHandler());
                            p.addLast(new HelloWorld2ClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            future.channel().writeAndFlush("hello Netty, Test AttributeMap");
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
