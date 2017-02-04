package heartbeat.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * Created by leo on 17-2-4.
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    private int lossConnectTime = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectTime++;
                System.out.println("5秒没有收到客户端的信息了");
                if (lossConnectTime > 2) {
                    System.out.println("关闭这个不活跃的channel" + new Date());
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead...");
        System.out.println(ctx.channel().remoteAddress() + "->server : " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception caught");
        cause.printStackTrace();
        ctx.close();
    }
}
