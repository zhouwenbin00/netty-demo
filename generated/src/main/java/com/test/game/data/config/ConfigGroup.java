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
    public final String CODE_VERSION = "2e88aa7a022544435660b6dabfbc6cb8";
    public final byte[] DATA_VERSION;
    public final Q_test1Group q_test1Group; // 新建 XLSX 工作表-测试表1

    public ConfigGroup(InputStream is, Factory2<byte[], Integer> factory) throws IOException {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        try {
            byte[] bytes = IOUtils.readBytes(is);
            ByteBufUtils.writeBytes(buf, bytes, 0, bytes.length);

            checkArgument(CODE_VERSION.equals(ByteBufUtils.readString(buf)), "配置文件和程序版本不一致");
            this.DATA_VERSION = factory.create(ByteBufUtils.readInt(buf));

            try {
                this.q_test1Group = new Q_test1Group(buf);
            } catch (Throwable e) {
                throw new com.test.game.core.exception.ConfigFileException(
                        e.getMessage(), "新建 XLSX 工作表-测试表1", e);
            }
        } finally {
            buf.release();
        }
    }

    public void check() {
        try {
            this.q_test1Group.check(this);
        } catch (Throwable e) {
            throw new com.test.game.core.exception.ConfigFileException(
                    e.getMessage(), "新建 XLSX 工作表-测试表1", e);
        }
    }
}
