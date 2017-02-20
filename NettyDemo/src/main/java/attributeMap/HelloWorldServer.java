package attributeMap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by leo on 17-2-4.
 */
public class HelloWorldServer {

    private int port;

    public HelloWorldServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decode", new StringDecoder());
                            socketChannel.pipeline().addLast("encode", new StringEncoder());
                            socketChannel.pipeline().addLast(new HelloWorldServerHandler());
                        }
                    })
                    //ChannelOption.SO_BACKLOG BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器
                    //请求处理线程全满时，用于临时存放已完成的请求的队列的最大长度。如果未设置或所设置值小于1，默认
                    //值是50
            .option(ChannelOption.SO_BACKLOG, 128)
                    //ChannelOption.SO_KEEPALIVE 是否启动心跳保活机制。在双方TCP套接字建立连接后（即进入ESTABLISHED状态）
                    //并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
            .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("Server start listen at " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8000;
        }
        new HelloWorldServer(port).start();
    }
}
