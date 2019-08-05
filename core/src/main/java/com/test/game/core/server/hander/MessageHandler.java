package com.test.game.core.server.hander;

import com.test.game.core.net.Governor;
import com.test.game.core.net.message.Message;
import com.test.game.core.base.Factory;
import com.test.game.core.utils.NetUtils;
import com.test.game.core.utils.Num;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/** @Auther: zhouwenbin @Date: 2019/8/3 16:13 */
public abstract class MessageHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    // channel计数器
    private final AtomicInteger channelCount = new AtomicInteger(Num.ZERO);
    private final String name;
    private final AttributeKey<Governor> governorKey;
    // 限速器
    private final Factory<Governor> governorFactory;
    // channel集合
    private final ThreadLocal<Set<Channel>> localChannels = new ThreadLocal<>();

    public MessageHandler(String name, @Nullable Factory<Governor> governorFactory) {
        this.name = name;
        this.governorFactory = governorFactory;
        if (this.governorFactory != null) {
            this.governorKey = NetUtils.createAttributeKey(Governor.class);
        } else {
            this.governorKey = null;
        }
    }

    private Set<Channel> getNotNullChannelSet() {
        Set<Channel> channels = this.localChannels.get();
        if (channels != null) {
            return channels;
        } else {
            channels = new HashSet<>();
            this.localChannels.set(channels);
            return channels;
        }
    }

    @Override
    public final void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 加入集合
        this.getNotNullChannelSet().add(ctx.channel());
        // 添加等待失败时间
        ctx.channel().attr(NetUtils.WRITE_FAIL_TIMES).set(new AtomicInteger());
        if (this.governorFactory != null) {
            // 添加限速器
            ctx.channel().attr(this.governorKey).set(governorFactory.create());
        }
        log.info("[{}] connected [{}] [{}]", ctx.channel(), name, channelCount.incrementAndGet());
        onChannelActive(ctx.channel());
    }

    @Override
    public final void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 移除
        this.getNotNullChannelSet().remove(ctx.channel());
        ctx.channel().attr(NetUtils.WRITE_FAIL_TIMES).remove();
        if (governorFactory != null) {
            ctx.channel().attr(governorKey).remove();
        }
        log.info(
                "[{}] disconnected [{}] [{}]", ctx.channel(), name, channelCount.decrementAndGet());
        onChannelInactive(ctx.channel());
    }

    @Override
    public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Message)) {
            throw new IllegalArgumentException("msg type error!" + msg.getClass().getName());
        }
        if (governorFactory != null) {
            Governor governor = ctx.channel().attr(governorKey).get();
            governor.overSpeed();
        }
        onChannelRead(ctx.channel(), (Message) msg);
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[{}][{}]", name, NetUtils.host(ctx), cause);
        // 关闭channel
        NetUtils.close(ctx.channel(), cause.getClass().getSimpleName());
    }

    protected abstract void onChannelActive(Channel channel);

    protected abstract void onChannelRead(Channel channel, Message msg);

    protected abstract void onChannelInactive(Channel channel);

    //    public void tick(long time) {}
}
