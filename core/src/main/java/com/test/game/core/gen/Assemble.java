package com.test.game.core.gen;

import com.google.common.collect.HashBasedTable;
import com.test.game.core.net.message.Message.NodeType;
import com.google.common.collect.Table;
import com.test.game.core.utils.StringUtils;

import java.util.*;

/** @Auther: zhouwenbin @Date: 2019/8/13 11:21 */
public class Assemble {
    public final As3Result as3Result;
    public final JavaResult javaResult;

    public Assemble(As3Result as3Result, JavaResult javaResult) {
        this.as3Result = as3Result;
        this.javaResult = javaResult;
    }

    static class TmpBean {
        List<ErrorMessage> errors = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        List<Handler> handlers = new ArrayList<>();
        final MessageGroup mg;
        final HandlerGroup hg;
        final JavaGuiceModule jgm;

        public TmpBean(String pkg, NodeType from, NodeType to) {
            String prefix =
                    StringUtils.upperFirstAndLowerOther(from.name())
                            + "To"
                            + StringUtils.upperFirstAndLowerOther(to.name());
            this.mg =
                    new MessageGroup(
                            pkg,
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            prefix + "MessageGroup");
            this.hg =
                    new HandlerGroup(
                            pkg + ".hg",
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            prefix + "HandlerGroup",
                            pkg + ".handler." + prefix + "Handler");
            this.jgm =
                    new JavaGuiceModule(
                            pkg + ".guice",
                            prefix + "GuiceModule",
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>());
        }

        private void add0(AbstractMessage message, boolean ignoreHandler) {
            this.mg.add(message);
            if (!ignoreHandler) {
                Handler handler =
                        new Handler(
                                message.getPackage(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                message.getId(),
                                message.getName() + "Handler",
                                StringUtils.upperFirstAndLowerOther(message.getFrom())
                                        + "To"
                                        + StringUtils.upperFirstAndLowerOther(message.getTo())
                                        + "Handler",
                                message.getName(),
                                message.getDesc(),
                                message.getClient_package(),
                                message.getClient_name(),
                                message.getCfgPkg());
                this.handlers.add(handler);
                this.hg.add(handler);
                this.jgm.add(handler);
            }
        }

        public void add(Message message, boolean ignoreHandler) {
            this.add0(message, ignoreHandler);
            this.messages.add(message);
        }

        public void addError(ErrorMessage errorMessage, boolean ignoreHandler) {
            this.add0(errorMessage, ignoreHandler);
            this.errors.add(errorMessage);
        }
    }

    public static Assemble assemble(MessageParser parser) {
        List<Bean> beans = new ArrayList<>();
        List<Enum> enums = new ArrayList<>();
        Table<NodeType, NodeType, TmpBean> tmpBeans = HashBasedTable.create();
        for (NodeType from : NodeType.values()) {
            for (NodeType to : NodeType.values()) {
                if (from != to) {
                    tmpBeans.put(from, to, new TmpBean(parser.pkg, from, to));
                }
            }
        }

        for (OriginBean ob : parser.obs) {
            beans.add(assembly(ob));
        }

        for (OriginEnum oe : parser.oes) {
            enums.add(assembly(oe));
        }

        for (OriginMessage om : parser.oms) {
            tmpBeans.get(om.from, om.to)
                    .add(
                            createMessage(
                                    om,
                                    om.request,
                                    om.from,
                                    om.to,
                                    StringUtils.upperFirstAndLowerOther(MessageType.Request.name()),
                                    om.requestID),
                            om.ignoreRequestHandler);
            if (om.response != null) {
                tmpBeans.get(om.to, om.from)
                        .add(
                                createMessage(
                                        om,
                                        om.response,
                                        om.to,
                                        om.from,
                                        StringUtils.upperFirstAndLowerOther(
                                                MessageType.Response.name()),
                                        om.responseID),
                                om.ignoreResponseHandler);
            }

            if (om.error != null) {
                tmpBeans.get(om.to, om.from).addError(createError(om), om.ignoreErrorHandler);
            }
        }

        Version version = new Version(parser.pkg, parser.version(), "MessageVersion");
        As3Result as3Result =
                new As3Result(
                        beans,
                        enums,
                        mergeErrors(
                                tmpBeans.column(NodeType.CLIENT).values(),
                                tmpBeans.row(NodeType.CLIENT).values()),
                        mergeMessages(
                                tmpBeans.column(NodeType.CLIENT).values(),
                                tmpBeans.row(NodeType.CLIENT).values()),
                        version,
                        mergeHandlers(tmpBeans.column(NodeType.CLIENT).values()),
                        mergeMg(
                                parser.pkg,
                                "MessageGroup",
                                tmpBeans.column(NodeType.CLIENT).values()),
                        mergeHg(
                                parser.pkg,
                                "HandlerGroup",
                                tmpBeans.column(NodeType.CLIENT).values()));
        JavaResult javaResult =
                new JavaResult(
                        beans,
                        enums,
                        mergeErrors(tmpBeans.values()),
                        mergeMessages(tmpBeans.values()),
                        version,
                        mergeHandlers(tmpBeans.values()));
        Iterator var19 = tmpBeans.values().iterator();

        while (var19.hasNext()) {
            Assemble.TmpBean tmpBean = (Assemble.TmpBean) var19.next();
            javaResult.mgs.add(tmpBean.mg);
            javaResult.hgs.add(tmpBean.hg);
            javaResult.jgms.add(tmpBean.jgm);
        }

        return new Assemble(as3Result, javaResult);
    }

    private static HandlerGroup mergeHg(String pkg, String name, Collection<TmpBean> values) {
        Set<String> ji = new HashSet<>();
        Set<String> ai = new HashSet<>();
        Set<Handler> handlers = new HashSet<>();

        for (TmpBean value : values) {
            ji.addAll(value.hg.getJavaImports());
            ai.addAll(value.hg.getAs3Imports());
            handlers.addAll(value.hg.getHandlers());
        }

        return new HandlerGroup(pkg, new ArrayList<>(ji), new ArrayList<>(ai), new ArrayList<>(handlers), name, (String)null);
    }

    private static MessageGroup mergeMg(String pkg, String name, Collection<TmpBean> values) {
        Set<String> ji = new HashSet<>();
        Set<String> ai = new HashSet<>();
        Set<AbstractMessage> messages = new HashSet<>();

        for (TmpBean value : values) {
            ji.addAll(value.mg.getJavaImports());
            ai.addAll(value.mg.getAs3Imports());
            messages.addAll(value.mg.getMessages());
        }

        return new MessageGroup(pkg, new ArrayList<>(ji), new ArrayList<>(ai), new ArrayList<>(messages), name);
    }

    private static List<Handler> mergeHandlers(Collection<TmpBean> values) {
        Set<Handler> set = new HashSet<>();

        for (TmpBean value : values) {
            set.addAll(value.handlers);
        }

        return new ArrayList<>(set);
    }

    @SafeVarargs
    private static List<Message> mergeMessages(Collection<TmpBean>... valuess) {
        Set<Message> set = new HashSet<>();
        for (Collection<TmpBean> values : valuess) {
            for (TmpBean value : values) {
                set.addAll(value.messages);
            }
        }

        return new ArrayList<>(set);
    }

    @SafeVarargs
    private static List<ErrorMessage> mergeErrors(Collection<TmpBean>... valuess) {
        Set<ErrorMessage> set = new HashSet<>();
        for (Collection<TmpBean> values : valuess) {
            for (TmpBean value : values) {
                set.addAll(value.errors);
            }
        }

        return new ArrayList<>(set);
    }

    private static ErrorMessage createError(OriginMessage om) {
        return new ErrorMessage(
                om.pkg,
                new NameAndDesc(
                        om.name + StringUtils.upperFirstAndLowerOther(MessageType.Error.name()),
                        om.desc),
                null,
                om.errorID,
                om.to,
                om.from,
                convert0(om.error),
                om.getClient_package(),
                om.getClient_name(om.to, om.from) + "Error",
                om.getCfgPkg());
    }

    private static List<EnumField> convert0(List<NameAndDesc> errors) {
        List<EnumField> list = new ArrayList<>();
        int index = 1;

        for (NameAndDesc error : errors) {
            list.add(new EnumField(error, index++));
        }

        return list;
    }

    private static Bean assembly(OriginBean ob) {
        List<BeanField> list = new ArrayList<>();

        for (OriginField field : ob.fields) {
            list.add(new BeanField(field, field.field));
        }

        return new Bean(
                ob.pkg, ob, list, ob.getClient_package(), ob.getClient_name(), ob.getCfgPkg());
    }

    private static Enum assembly(OriginEnum oe) {
        return new Enum(oe, oe.pkg, assembly(oe.fields, oe.start));
    }

    private static List<EnumField> assembly(List<NameAndDesc> fields, int start) {
        List<EnumField> list = new ArrayList<>();
        int index = start;

        for (NameAndDesc field : fields) {
            list.add(new EnumField(field, index++));
        }

        return list;
    }

    private static Message createMessage(
            OriginMessage om,
            List<OriginField> fields,
            NodeType from,
            NodeType to,
            String suffix,
            int id) {
        return new Message(
                om.pkg,
                new NameAndDesc(om.name + suffix, om.desc),
                convert(fields),
                id,
                from,
                to,
                om.error == null
                        ? null
                        : om.name + StringUtils.upperFirstAndLowerOther(MessageType.Error.name()),
                om.getClient_package(),
                om.getClient_name(from, to),
                om.getCfgPkg());
    }

    private static List<BeanField> convert(List<OriginField> fields) {
        List<BeanField> list = new ArrayList<>();

        for (OriginField field : fields) {
            list.add(new BeanField(field, field.field));
        }

        return list;
    }
}
