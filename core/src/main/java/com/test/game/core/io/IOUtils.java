package com.test.game.core.io;

import com.google.common.base.Preconditions;
import com.test.game.core.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther: zhouwenbin
 * @Date: 2019/8/10 00:22
 */
public class IOUtils {
    public static byte[] readBytes(InputStream is) throws IOException {
        Preconditions.checkNotNull(is);

        try {
            ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

            try {
                byte[] tmp = new byte[1024];

                int read;
                while((read = is.read(tmp)) > 0) {
                    ByteBufUtils.writeBytes(buf, tmp, 0, read);
                }

                byte[] var4 = ByteBufUtils.toByteArray(buf);
                return var4;
            } finally {
                buf.release();
            }
        } finally {
            is.close();
        }
    }
}
