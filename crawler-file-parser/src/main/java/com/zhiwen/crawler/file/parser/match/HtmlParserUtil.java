package com.zhiwen.crawler.file.parser.match;

import com.zhiwen.crawler.common.util.UrlUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhiwenzhu on 17/1/17.
 */
public class HtmlParserUtil {
    // 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
    private static final NodeFilter FRAME_FILTER = new NodeFilter() {
        public boolean accept(Node node) {
            if (node.getText().startsWith("frame src=")) {
                return true;
            } else {
                return false;
            }
        }
    };

    private static final NodeFilter A_FILTER = new NodeClassFilter(LinkTag.class);

    private static final NodeFilter LINK_FILTER = new NodeFilter() {
        public boolean accept(Node node) {
            String text = node.getText();
            if (text.startsWith("link")) {
                return true;
            } else {
                return false;
            }
        }
    };

    private static final NodeFilter[] FILTER_ARRAY = new NodeFilter[] {FRAME_FILTER, A_FILTER, LINK_FILTER};


    public static Set<String> extractLinks(String url, String pageContent) {

        Set<String> links = new HashSet<String>();
        try {
            Parser parser = createParser(pageContent);




            // OrFilter 接受<a>标签或<frame>标签，注意NodeClassFilter()可用来过滤一类标签，linkTag对应<a>标签
            OrFilter urlFilter = new OrFilter(FILTER_ARRAY);

            // 得到所有经过过滤的标签，结果为NodeList
            NodeList list = parser.extractAllNodesThatMatch(urlFilter);

            for (int i = 0; i < list.size(); i++) {
                Node tag = list.elementAt(i);
                String text = tag.getText();
                if (tag instanceof LinkTag)// <a> 标签
                {
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink();// 调用getLink()方法得到<a>标签中的链接
                    linkUrl = preHandleUrl(url, linkUrl);
                    links.add(linkUrl);
                } else if (text.startsWith("frame src=")){
                    // <frame> 标签
                    // 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
                    int start = text.indexOf("src=");
                    if (start != -1) {
                        text = text.substring(start + 5);
                        int end = text.indexOf("\"");
//                        if (end == -1) {
//                            end = text.indexOf(">");
//                        }
                        if (end != -1) {
                            String frameUrl = text.substring(0, end);
                            frameUrl = preHandleUrl(url, frameUrl);
                            links.add(frameUrl);
                        }
                    }
                } else {
                    int start = text.indexOf("href=");
                    if (start != -1) {
                        text = text.substring(start + 6);
                        int end = text.indexOf("\"");
//                        if (end == -1) {
//                            end = text.indexOf(">");
//                        }
                        if (end != -1) {
                            String linkUrl = text.substring(0, end);
                            linkUrl = preHandleUrl(url, linkUrl);
                            links.add(linkUrl);
                        }
                    }
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

    private static Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        return new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
}
