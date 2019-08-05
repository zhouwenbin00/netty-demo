package com.test.game.core.net;

import io.netty.buffer.ByteBuf;

/** @Auther: zhouwenbin @Date: 2019/8/5 13:39 */
public abstract class Bean<T> {

    abstract void write(ByteBuf byteBuf);

    abstract T read(ByteBuf byteBuf);
}
