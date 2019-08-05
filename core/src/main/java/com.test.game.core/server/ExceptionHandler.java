package com.test.game.core.server;

import com.test.game.core.utils.NetUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @Auther: zhouwenbin @Date: 2019/8/3 16:22 */
public class ExceptionHandler extends ChannelDuplexHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ExceptionHandler() {}

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        this.log.error("exceptionCaught", cause);
        NetUtils.close(ctx.channel(), cause.getClass().getSimpleName());
    }
}
