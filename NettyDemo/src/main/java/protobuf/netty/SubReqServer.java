package protobuf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import nio.TimeServerHandler;
import protobuf.SubscribeReqProto;


/**
 * Created by leo on 16-8-24.
 */
public class SubReqServer {

    public void bind(int port) throws Exception {
        // 配置服务端的NIO线程组
        // Reactor线程组
        // 一个用于服务端接受客户端的连接,另一个用于进行SocketChannel的网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // NIO服务端的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //.handler(new LoggingHandler(LogLevel.INFO))

                    // 作用类似于Reactor模式中的Handler类,主要处理网络I/O事件,
                    // 例如记录日志,对消息进行编解码
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder()); // 处理半包消息
                            ch.pipeline().addLast(new ProtobufDecoder(// ProtobufDecoder仅仅负责解码,不支持读半包
                                    SubscribeReqProto.SubscribeReq.getDefaultInstance()
                            ));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            // 绑定端口,同步等待成功
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出,释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



    public static void main(String[] args) throws Exception {
        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        new SubReqServer().bind(port);
    }
}
