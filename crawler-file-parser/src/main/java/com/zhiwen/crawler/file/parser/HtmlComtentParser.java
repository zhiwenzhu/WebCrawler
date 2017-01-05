package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawer.fetcher.FetcherPageContent;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhiwenzhu on 17/1/4.
 */
public class HtmlComtentParser {
    private String fetcherPageContent(String url) {
        return FetcherPageContent.fetcherPage(url);
    }

    private static final String TITLE_REGEX = "<title>.*</title>";
    private static final String KEYWORDS_REGEX = "name=\"keywords\" content=\"[^>]*";
    private static final String URLS_REGEX = "href=http://\\S+";
    private static final String DESCRIPTION_REGEX = "";

    private static final String HEAD_REGEX = "<head>.*</head>";

    private static final String BODY_REGEX = "<body.*</body>";

    private void getUrlFromPageContent(String pageContent) {
        Set<String> urlSet = new HashSet<String>();
        String head = preHandlePageContent(pageContent, HEAD_REGEX);
        String body = preHandlePageContent(pageContent, BODY_REGEX);

        Matcher matcher = getMatcher(body, URLS_REGEX);
        Matcher titleMatcher = getMatcher(head, TITLE_REGEX);
        Matcher keywordsMatcher = getMatcher(head, KEYWORDS_REGEX);
        Matcher descriptionMatcher = getMatcher(head, DESCRIPTION_REGEX);


        System.out.println(head);

        System.out.println(body);

    }

    private Matcher getMatcher(String pageContent, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);
        return matcher;
    }

    //预处理pageContent，得到head部分或者body部分的字符串；
    private String preHandlePageContent(String pageContent, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);

        String result = matcher.find()? matcher.group() : "";
        return result;
    }

    public void run(String url) {
        String page = fetcherPageContent(url);

        getUrlFromPageContent(page);
    }

    public static void main(String[] args) {
        HtmlComtentParser hp = new HtmlComtentParser();

        hp.run("www.baidu.com");
    }

}
