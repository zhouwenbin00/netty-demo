package com.test.game.core.net.message;

import io.netty.buffer.ByteBuf;

/** @Auther: zhouwenbin @Date: 2019/8/5 13:39 */
public abstract class Bean<T> {

    public abstract void write(ByteBuf byteBuf);

    public abstract T read(ByteBuf byteBuf);
}
