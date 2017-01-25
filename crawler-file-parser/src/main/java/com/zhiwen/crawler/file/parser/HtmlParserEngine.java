package com.zhiwen.crawler.file.parser;

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
public class HtmlParserEngine {

    // 过滤 <frame >标签的 filter，用来提取 frame 标签里的 src 属性所表示的链接
    private final NodeFilter FRAME_FILTER = new NodeFilter() {
        public boolean accept(Node node) {
            return isFrameNode(node.getText());
        }
    };

    private final NodeFilter A_FILTER = new NodeClassFilter(LinkTag.class);

    private final NodeFilter LINK_FILTER = new NodeFilter() {
        public boolean accept(Node node) {
            String text = node.getText();
            return text.startsWith("link");
        }
    };

    private final NodeFilter[] FILTER_ARRAY = new NodeFilter[]{FRAME_FILTER, A_FILTER, LINK_FILTER};


    public Set<String> extractLinks(String url, String pageContent) throws ParserException {

        Set<String> links = new HashSet<String>();
        Parser parser = createParser(pageContent);
        OrFilter urlFilter = new OrFilter(FILTER_ARRAY);
        NodeList list = parser.extractAllNodesThatMatch(urlFilter);
        for (int i = 0; i < list.size(); i++) {
            Node tag = list.elementAt(i);
            parseNode(url, links, tag);
        }
        return links;
    }

    private void parseNode(String url, Set<String> links, Node tag) {
        String text = tag.getText();

        if (isLinkNode(tag)) {
            parseLinkNode(url, links, (LinkTag) tag);
        } else if (isFrameNode(text)) {
            parseFrameNode(url, links, text);
        } else {
            parseOtherHrefNode(url, links, text);
        }
    }

    private boolean isFrameNode(String text) {
        return text.startsWith("frame src=");
    }

    private boolean isLinkNode(Node tag) {
        return tag instanceof LinkTag;
    }

    private void parseOtherHrefNode(String url, Set<String> links, String text) {
        int start = text.indexOf("href=");
        if (start != -1) {
            text = text.substring(start + 6);
            int end = text.indexOf("\"");
            if (end != -1) {
                String linkUrl = text.substring(0, end);
                linkUrl = preHandleUrl(url, linkUrl);
                links.add(linkUrl);
            }
        }
    }

    private void parseFrameNode(String url, Set<String> links, String text) {
        int start = text.indexOf("src=");
        if (start != -1) {
            text = text.substring(start + 5);
            int end = text.indexOf("\"");
            if (end != -1) {
                String frameUrl = text.substring(0, end);
                frameUrl = preHandleUrl(url, frameUrl);
                links.add(frameUrl);
            }
        }
    }

    private void parseLinkNode(String url, Set<String> links, LinkTag tag) {
        String linkUrl = tag.getLink();// 调用getLink()方法得到<a>标签中的链接
        linkUrl = preHandleUrl(url, linkUrl);
        links.add(linkUrl);
    }

    private String preHandleUrl(String baseUrl, String thisUrl) {

        return UrlUtils.combine(baseUrl, thisUrl);
    }

    private Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        return new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
}
