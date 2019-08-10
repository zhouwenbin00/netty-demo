package com.test.game.data.config;

import com.test.game.core.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/** Created by FreeMarker. DO NOT EDIT!!! 新建 XLSX 工作表-测试表1 */
public class Q_test1 {
    public final int q_id; // id
    public final String q_name; // name
    public final boolean q_sss; // 空格测试 回车测试	是是是

    public static class Builder {
        public final int q_id; // id
        public final String q_name; // name
        public final boolean q_sss; // 空格测试 回车测试	是是是

        protected Builder(ByteBuf buf) {
            try {
                this.q_id = ByteBufUtils.readInt(buf);
            } catch (Throwable e) {
                throw new com.test.game.core.exception.ConfigFieldException(
                        e.getMessage(), "q_id", e);
            }
            try {
                this.q_name = ByteBufUtils.readString(buf);
            } catch (Throwable e) {
                throw new com.test.game.core.exception.ConfigFieldException(
                        e.getMessage(), "q_name", e);
            }
            try {
                this.q_sss = ByteBufUtils.readBoolean(buf);
            } catch (Throwable e) {
                throw new com.test.game.core.exception.ConfigFieldException(
                        e.getMessage(), "q_sss", e);
            }
        }
    }

    protected Q_test1(ByteBuf buf) {
        Builder builder = new Builder(buf);
        this.q_id = builder.q_id;
        this.q_name = builder.q_name;
        this.q_sss = builder.q_sss;
    }

    protected Q_test1(Q_test1 cfg) {
        this.q_id = cfg.q_id;
        this.q_name = cfg.q_name;
        this.q_sss = cfg.q_sss;
    }

    public abstract static class Group {

        private Q_test1[] datas;

        protected Group(ByteBuf buf) {
            datas = new Q_test1[ByteBufUtils.readInt(buf)];
            for (int i = 0; i < datas.length; ++i) {
                try {
                    Q_test1 data = new Q_test1(buf);
                    datas[i] = data;
                } catch (Throwable e) {
                    throw new com.test.game.core.exception.ConfigRowException(
                            e.getMessage(), i + 6, e);
                }
            }
        }

        protected final Q_test1[] getAndSetNull() {
            Q_test1[] r = get();
            this.datas = null;
            return r;
        }

        protected final Q_test1[] get() {
            return datas;
        }
    }
}
