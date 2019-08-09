package com.test.game.data.config;

import com.test.game.core.base.Factory2;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import com.test.game.core.io.IOUtils;
import com.test.game.core.utils.ByteBufUtils;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkArgument;

/** Created by FreeMarker. DO NOT EDIT!!! */
public class ConfigGroup {
    public final String CODE_VERSION = "d41d8cd98f00b204e9800998ecf8427e";
    public final byte[] DATA_VERSION;

    public ConfigGroup(InputStream is, Factory2<byte[], Integer> factory) throws IOException {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        try {
            byte[] bytes = IOUtils.readBytes(is);
            ByteBufUtils.writeBytes(buf, bytes, 0, bytes.length);

            checkArgument(CODE_VERSION.equals(ByteBufUtils.readString(buf)), "配置文件和程序版本不一致");
            this.DATA_VERSION = factory.create(ByteBufUtils.readInt(buf));

        } finally {
            buf.release();
        }
    }

    public void check() {
    }
}
