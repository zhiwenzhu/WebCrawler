package com.zhiwen.crawler.file.parser.match;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Created by zhiwenzhu on 17/1/6.
 * 该类用来匹配Html <body></body> 标签中的内容，所有的url从body中查找
 */
public class BodyMatchStrategy {
    //匹配url的正则，目前写的还不太好。。。
    private static final String URLS_REGEX = "href=\"?http://\\S+[^\"]";

    //匹配<body></body> 标签中的内容；
    private static final String BODY_REGEX = "<body.*</body>";

    /**
     * 对给定的html页面内容进行解析匹配，得到所有符合要求的url的集合
     * @param pageContent html页面的字符串
     * @return返回url集合
     */
    public static Set<String> getUrlFromPageContent(String pageContent) {
        Set<String> urlSet = new HashSet<String>();

        //先得到<body></body>标签内的内容
        String body = MatchUtil.preHandlePageContent(pageContent, BODY_REGEX);

        //匹配body中的url
        Matcher matcher = MatchUtil.getMatcher(body, URLS_REGEX);

        //便于测试
        System.out.println(body);

        while (matcher.find()) {
            String url = outUslessCharOfUrl(matcher.group());
            if (url.length() < 100) {
                urlSet.add(url);
            }

        }

        //便于测试
        for (String url : urlSet) {
            System.out.println(url);
        }

        return urlSet;

    }

    /**
     * 对匹配出来的包含url的字符串内容进行进一步处理，去掉头尾无效的信息
     * @param url 一条包含url的字符串
     * @return 一个文字表面上合法的url
     */
    private static String outUslessCharOfUrl(String url) {
        url = url.replaceAll("href=|\"|>.*|<.*", "");
        return url;
    }


}
