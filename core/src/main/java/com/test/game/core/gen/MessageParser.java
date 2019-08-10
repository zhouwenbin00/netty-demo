package com.test.game.core.gen;

import java.util.List;

/** @Auther: zhouwenbin @Date: 2019/8/10 16:55 */
public class MessageParser {
    public final String pkg;
    public final List<OriginBean> obs;
    public final List<OriginEnum> oes;
    public final List<OriginMessage> oms;

    public MessageParser(
            String pkg, List<OriginBean> obs, List<OriginEnum> oes, List<OriginMessage> oms) {
        this.pkg = pkg;
        this.obs = obs;
        this.oes = oes;
        this.oms = oms;
    }
}
