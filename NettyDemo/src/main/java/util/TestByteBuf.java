package util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by leo on 16-8-25.
 */
public class TestByteBuf {

    public static void main(String[] args) {
        byte[] bytes = new byte[] {1, 2, 3};
        ByteBuf resp = Unpooled.copiedBuffer(bytes);
        System.out.println("resp.capacity()" + resp.capacity());
        System.out.println("resp.maxCapacity()" + resp.maxCapacity());
        System.out.println("resp.maxWritableBytes()" + resp.maxWritableBytes());


    }
}
