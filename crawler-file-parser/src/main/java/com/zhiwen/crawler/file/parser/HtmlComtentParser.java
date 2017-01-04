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

    private void getUrlFromPageContent(String pageContent) {
        Set<String> urlSet = new HashSet<String>();

        String regex = "href=http://\\S+";
        String regexTitle = "<title>.*</title>";
//        String regex = "href=http://[^>]*";

        Pattern pattern = Pattern.compile(regex);
        Pattern title = Pattern.compile(regexTitle);

        Matcher matcher = pattern.matcher(pageContent);
        Matcher matcherTitle = title.matcher(pageContent);

        if (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.groupCount());
        }

        if (matcherTitle.find()) {
            System.out.println(matcherTitle.group());
            System.out.println(matcherTitle.groupCount());
            String s = matcherTitle.group();

        }

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
