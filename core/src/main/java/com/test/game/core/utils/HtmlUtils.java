package com.test.game.core.utils;

import com.google.common.base.Preconditions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/** @Auther: zhouwenbin @Date: 2019/8/9 01:24 */
public abstract class HtmlUtils {
    private HtmlUtils() {}

    public static String font2span(String fontHtml) {
        Document document = Jsoup.parse(fontHtml);
        StringBuilder sb = new StringBuilder();
        document.body()
                .children()
                .forEach(
                        (element) -> {
                            if (element.nodeName().equals("font")) {
                                sb.append(font2span(element));
                            } else if (element.nodeName().equals("br")) {
                                sb.append("<br/>");
                            }
                        });
        return sb.toString();
    }

    private static StringBuilder font2span(Element element) {
        Preconditions.checkArgument(element.nodeName().equals("font"));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < element.childNodeSize(); ++i) {
            Node node = element.childNode(i);
            if (node.nodeName().equals("font")) {
                sb.append(font2span((Element)node));
            } else if (node.nodeName().equals("br")) {
                sb.append("<br/>");
            } else {
                sb.append(spanStart(element)).append(node).append(spanEnd());
            }
        }

        return sb;
    }

    private static String spanStart(Element element) {
        StringBuilder sb = new StringBuilder("<span");
        if (element.attributes().size() > 0) {
            sb.append(" style='");
            element.attributes().forEach((attribute) -> {
                if (attribute.getKey().equals("color")) {
                    sb.append("color:").append(attribute.getValue()).append(";");
                } else if (attribute.getKey().equals("size")) {
                    sb.append("fontSize:").append(attribute.getValue()).append(";");
                }

            });
            sb.append("'");
        }

        sb.append(">");
        return sb.toString();
    }

    private static String spanEnd() {
        return "</span>";
    }
}
