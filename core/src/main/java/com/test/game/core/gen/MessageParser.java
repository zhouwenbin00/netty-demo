package com.test.game.core.gen;

import com.test.game.core.utils.StringUtils;

import java.util.Iterator;
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

    public String version() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.pkg);
        Iterator var2 = this.obs.iterator();

        while(var2.hasNext()) {
            OriginBean ob = (OriginBean)var2.next();
            ob.buildMD5(sb);
        }

        var2 = this.oes.iterator();

        while(var2.hasNext()) {
            OriginEnum oe = (OriginEnum)var2.next();
            oe.buildMD5(sb);
        }

        var2 = this.oms.iterator();

        while(var2.hasNext()) {
            OriginMessage om = (OriginMessage)var2.next();
            om.buildMD5(sb);
        }

        return StringUtils.md5(sb.toString());
    }
}
