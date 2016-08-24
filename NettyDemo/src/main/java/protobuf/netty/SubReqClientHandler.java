package protobuf.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protobuf.SubscribeReqProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 16-8-24.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {
    public SubReqClientHandler() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.
                SubscribeReq.
                newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("leo");
        builder.setProductName("Netty Book");
        List<String> address = new ArrayList<String>();
        address.add("NanJing");
        address.add("BeiJing");
        address.add("ShenZhen");
        builder.addAllAddress(address);
        return builder.build();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
