package heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 17-2-4.
 */
public class HeartBeatsClient {

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap bootstrap;

    private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

    public void connect(int port, String host) throws Exception {
        //Configure the client
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

        final ConnectionWatchdog watchdog = new ConnectionWatchdog(bootstrap, timer, port, host, true) {
            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        this,
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        new StringDecoder(),
                        new StringEncoder(),
                        new HeartBeatClientHandler()
                };
            }
        };

        ChannelFuture future = null;
        //进行连接
        try {
            synchronized (bootstrap) {
                bootstrap.handler(new ChannelInitializer<Channel>() {
                    //初始化channel
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(watchdog.handlers());
                    }
                });
                future = bootstrap.connect(host, port);
            }

            //以下代码在synchronized同步块外面是安全的
            future.sync();
        } catch (Throwable t) {
            throw new Exception("connects to fails", t);
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
