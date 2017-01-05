package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.fetcher.FetcherPageContent;
import com.zhiwen.crawler.file.store.model.FileMessage;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import com.zhiwen.crawler.file.store.util.SpringBeanUtil;
import com.zhiwen.crawler.url.store.model.Urls;
import com.zhiwen.crawler.url.store.spi.UrlsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhiwenzhu on 17/1/4.
 */

//@Component
public class HtmlContentParser {
//    @Autowired
    private FileMessageService fileMessageService;
//    @Autowired
    private UrlsService urlsService;


    //调用FetcherPageContent的静态方法通过给定的url得到网页内容，以字符串形式返回；
    private String fetcherPageContent(String url) {
        return FetcherPageContent.fetcherPage(url);
    }

    //筛选html页面标题的正则；
    private static final String TITLE_REGEX = "<title>.*</title>";
    private static final String KEYWORDS_REGEX = "name=\"Keywords\" content=\"[^>]*";
    private static final String URLS_REGEX = "href=\"?http://\\S+[^\"]";
    private static final String DESCRIPTION_REGEX = "name=\"Description\" content=\"[^>]*";
    private static final String HEAD_REGEX = "<head>.*</head>";
    private static final String BODY_REGEX = "<body.*</body>";

    //根据给定的页面内容，解析并得到所有符合条件的url；
    private Set<String> getUrlFromPageContent(String pageContent) {
        Set<String> urlSet = new HashSet<String>();
        String body = preHandlePageContent(pageContent, BODY_REGEX);

        Matcher matcher = getMatcher(body, URLS_REGEX);

        // TODO: 17/1/5 先使得到的结果是所有筛选出来的url；然后将所有url存入集合中；返回集合或者直接存入数据库；

        System.out.println(body);

        while (matcher.find()) {
            String url = outUslessCharOfUrl(matcher.group());
            urlSet.add(url);
        }

        //urlset 里面加入解析得到的所有url；
        for (String url : urlSet) {
            System.out.println(url);
        }

        return urlSet;

    }

    private FileMessage getMessageFromPageContent(String pageContent) {
        FileMessage fm = new FileMessage();
        String head = preHandlePageContent(pageContent, HEAD_REGEX);
        Matcher titleMatcher = getMatcher(head, TITLE_REGEX);
        Matcher keywordsMatcher = getMatcher(head, KEYWORDS_REGEX);
        Matcher descriptionMatcher = getMatcher(head, DESCRIPTION_REGEX);

        if (titleMatcher.find()) {
            String s = titleMatcher.group();
            String title = s.substring(7, s.length() - 8);
            fm.setTitle(title);
        }

        if (keywordsMatcher.find()) {
            String s = keywordsMatcher.group();
            String keywords = s.substring(25, s.length() - 3);
            fm.setKeywords(keywords);
        }

        if (descriptionMatcher.find()) {
            String s = descriptionMatcher.group();
            String des = s.substring(28, s.length() - 3);
            fm.setDescription(des);
        }

        return fm;

//        System.out.println(head);

    }
    private Matcher getMatcher(String pageContent, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        Pattern pattern = Pattern.compile(regex);
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
        Set<String> urlSet = getUrlFromPageContent(page);

        FileMessage fm = getMessageFromPageContent(page);
        fm.setUrl(url);
        System.out.println("1:" + fm.getTitle());
        System.out.println("2:" + fm.getUrl());
        System.out.println("3:" + fm.getKeywords());
        System.out.println("4:" + fm.getDescription());
        //把页面提取出来的信息存入持久层；先判断书库库中是否有该页面信息
        //多线程时，此处应该加锁
        fileMessageService = (FileMessageService) SpringBeanUtil.getBean("FileMessageService");

        if (fileMessageService.getFileMessageByUrl(url) == null) {
            fileMessageService.addFileMessage(fm);

            List<Urls> urlss = genUrls(urlSet, url);
            urlsService.addUrlss(urlss);
        }

    }

    private List<Urls> genUrls(Set<String> urls, String parentUrl) {
        List<Urls> urlss = new LinkedList<Urls>();
        for (String url : urls) {

            if (fileMessageService.getFileMessageByUrl(url) == null && urlsService.getUrlsByUrl(url) == null
                    && urlsService.getUrlsByParentUrl(url) == null) {
                Urls temp = new Urls();
                temp.setUrl(url);
                temp.setParentUrl(parentUrl);
                urlss.add(temp);
            }
        }

        return urlss;
    }


    private String outUslessCharOfUrl(String url) {
        url = url.replaceAll("href=|\"|>.*|<.*", "");
        return url;
    }

    public static void main(String[] args) {
        HtmlContentParser hp = new HtmlContentParser();

//        hp.run("www.baidu.com");
//        hp.run("www.163.com");
//        hp.run("http://news.baidu.com/");
        hp.run("http://tool.oschina.net/apidocs/apidoc?api=jdk-zh");

    }

}
