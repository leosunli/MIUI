package attributeMap;

import io.netty.util.AttributeKey;

/**
 * Created by leo on 17-2-4.
 */
public class AttributeMapConstant {

    public static final AttributeKey<NettyChannel> NETTY_CHANNEL_KEY =
            AttributeKey.valueOf("netty.channel");
}
