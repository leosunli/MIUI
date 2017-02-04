package heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

/**
 * 重连检测狗，当发现当前的链路不稳定关闭之后，进行12次重连
 */
@ChannelHandler.Sharable
public abstract class ConnectionWatchdog extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {

    private final Bootstrap bootstrap;

    private final Timer timer;

    private final int port;

    private final String host;

    private volatile boolean reconnect = true;

    private int attempts;

    public ConnectionWatchdog(Bootstrap bootstrap, Timer timer, int port, String host, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }

    /**
     * channel链路每次active的时候，将其连接的次数重新置为0
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("当前链路已经激活了，重连尝试次数重新置为0");
        attempts = 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链路关闭");
        if (reconnect) {
            System.out.println("链路关闭，将进行重连");
            if (attempts < 12) {
                attempts++;
                //重连的间隔时间会越来越长
                int timeout = 2 << attempts;
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }

    public void run(Timeout timeout) throws Exception {

        ChannelFuture future;
        //bootstrap已经初始化好了，只需要将handler填入就可以了
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host, port);
        }

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                boolean succeed = channelFuture.isSuccess();

                //如果重连失败，则调用ChannelInactive方法，再次发出重连事件，一直尝试12次，如果失败则不再重连
                if (!succeed) {
                    System.out.println("重连失败");
                    channelFuture.channel().pipeline().fireChannelInactive();
                } else {
                    System.out.println("重连成功");
                }
            }
        });
    }
}
