package com.test.game.core.gen;

import com.test.game.core.utils.ByteBufUtils;
import com.test.game.core.utils.ConfigUtils;
import io.netty.buffer.ByteBuf;

import java.util.List;

/** 带类的数据集合 @Auther: zhouwenbin @Date: 2019/8/11 15:54 */
public class DataWithClass {

    public final List<List<FieldWithValue>> dataList;
    public final Class clazz;

    public DataWithClass(List<List<FieldWithValue>> value, Class clazz) {

        this.dataList = value;
        this.clazz = clazz;
    }

    public void writeServer(ByteBuf buf) {
        if (ConfigUtils.log.isDebugEnabled()) {
            ConfigUtils.log.debug("write client:{}", this.clazz.className);
        }
        if (this.clazz.belong.isServer()) {
            ByteBufUtils.writeInt(buf, this.dataList.size());
            for (List<FieldWithValue> fieldWithValues : dataList) {
                for (FieldWithValue fieldWithValue : fieldWithValues) {
                    this.writeServer(buf, fieldWithValue);
                }
            }
        }
    }

    private void writeServer(ByteBuf buf, FieldWithValue f) {
        if (f.field.belong.isServer()) {
            f.write(buf, true);
        }
    }

    public void writeClient(ByteBuf buf) {
        if (ConfigUtils.log.isDebugEnabled()) {
            ConfigUtils.log.debug("write client:{}", this.clazz.className);
        }
        if (this.clazz.belong.isClient()) {
            ByteBufUtils.writeInt(buf, this.dataList.size());
            for (List<FieldWithValue> fieldWithValues : dataList) {
                for (FieldWithValue fieldWithValue : fieldWithValues) {
                    this.writeClient(buf, fieldWithValue);
                }
            }
        }
    }

    private void writeClient(ByteBuf buf, FieldWithValue f) {
        if (f.field.belong.isClient()) {
            f.write(buf, false);
        }
    }
}
