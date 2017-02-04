package attributeMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;

/**
 * Created by leo on 17-2-4.
 */
public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //Attribute<NettyChannel> attribute = ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        Attribute<NettyChannel> attribute = ctx.channel().attr(AttributeMapConstant.NETTY_CHANNEL_KEY);

        NettyChannel nettyChannel = attribute.get();
        if (nettyChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            nettyChannel = attribute.setIfAbsent(newNChannel);
        } else {
            System.out.println("channel active attribute map 中最新值");
            System.out.println(nettyChannel.getName() + "====" + nettyChannel.getCreateDate());
        }
        System.out.println("HelloWorld0ClientHandler Active");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Attribute<NettyChannel> attribute = ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        Attribute<NettyChannel> attribute = ctx.channel().attr(AttributeMapConstant.NETTY_CHANNEL_KEY);

        NettyChannel nettyChannel = attribute.get();
        if (nettyChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            nettyChannel = attribute.setIfAbsent(newNChannel);
        } else {
            System.out.println("channelRead attributeMap 中是有值的");
            System.out.println(nettyChannel.getName() + "=======" + nettyChannel.getCreateDate());
        }
        System.out.println("HelloWorld0ClientHandler read Message:" + msg);

        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
