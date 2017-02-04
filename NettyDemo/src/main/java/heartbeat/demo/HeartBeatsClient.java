package heartbeat.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 17-2-4.
 */
public class HeartBeatsClient {

    public void connect(int port, String host) throws Exception {
        //Configure the client
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture future = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                            pipeline.addLast("decode", new StringDecoder());
                            pipeline.addLast("encode", new StringEncoder());
                            pipeline.addLast(new HeartBeatClientHandler());
                        }
                    });

            future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            //group.shutdownGracefully();
            //重连
            if (future != null) {
                if (future.channel() != null && future.channel().isOpen()) {
                    future.channel().close();
                }
            }

            System.out.println("准备重连");
            connect(port, host);
            System.out.println("重连成功");
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {

            }
        }
        new HeartBeatsClient().connect(port, "127.0.0.1");
    }
}
