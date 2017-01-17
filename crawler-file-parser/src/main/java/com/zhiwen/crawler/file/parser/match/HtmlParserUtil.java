package com.zhiwen.crawler.file.parser.match;

import com.zhiwen.crawler.common.util.UrlUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhiwenzhu on 17/1/17.
 */
public class HtmlParserUtil {
    public static Set<String> extracLinks(String url, String pageContent) {

        Set<String> links = new HashSet<String>();
        try {
            Parser parser = new Parser(pageContent);

            // 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
            NodeFilter frameFilter = new NodeFilter() {
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            // OrFilter 接受<a>标签或<frame>标签，注意NodeClassFilter()可用来过滤一类标签，linkTag对应<a>标签
            OrFilter linkFilter = new OrFilter(new NodeClassFilter(
                    LinkTag.class), frameFilter);

            // 得到所有经过过滤的标签，结果为NodeList
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);

            for (int i = 0; i < list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag)// <a> 标签
                {
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();// 调用getLink()方法得到<a>标签中的链接
                    linkUrl = preHandleUrl(url, linkUrl);
                    links.add(linkUrl);
                } else{
                    // <frame> 标签
                    // 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
                    String frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1)
                        end = frame.indexOf(">");
                    String frameUrl = frame.substring(5, end - 1);
                    frameUrl = preHandleUrl(url, frameUrl);

                    links.add(frameUrl);
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return links;
    }

    private static String preHandleUrl(String baseUrl, String thisUrl) {

        return UrlUtils.combine(baseUrl, thisUrl);
    }
}
