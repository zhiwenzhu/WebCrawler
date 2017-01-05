package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.fetcher.FetcherPageContent;
import com.zhiwen.crawler.file.store.dao.FileMessageDao;
import com.zhiwen.crawler.file.store.model.FileMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhiwenzhu on 17/1/4.
 */
public class HtmlComtentParser {
    @Autowired
    private FileMessageDao fileMessageDao;


    //调用FetcherPageContent的静态方法通过给定的url得到网页内容，以字符串形式返回；
    private String fetcherPageContent(String url) {
        return FetcherPageContent.fetcherPage(url);
    }

    //筛选html页面标题的正则；
    private static final String TITLE_REGEX = "<title>.*</title>";
    private static final String KEYWORDS_REGEX = "name=\"keywords\" content=\"[^>]*";
    private static final String URLS_REGEX = "href=http://\\S+";
    private static final String DESCRIPTION_REGEX = "";
    private static final String HEAD_REGEX = "<head>.*</head>";
    private static final String BODY_REGEX = "<body.*</body>";

    //根据给定的页面内容，解析并得到所有符合条件的url；
    private void getUrlFromPageContent(String pageContent) {
        Set<String> urlSet = new HashSet<String>();
        String body = preHandlePageContent(pageContent, BODY_REGEX);

        Matcher matcher = getMatcher(body, URLS_REGEX);

        // TODO: 17/1/5 先使得到的结果是所有筛选出来的url；然后将所有url存入集合中；返回集合或者直接存入数据库；

        System.out.println(body);

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
            String keywords = s.substring(25, 2);
            fm.setKeywords(keywords);
        }

        if (descriptionMatcher.find()) {
            String s = descriptionMatcher.group();
            String des = s.substring(28, 2);
            fm.setDescription(des);
        }

        return fm;

//        System.out.println(head);

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

        FileMessage fm = getMessageFromPageContent(page);
        fm.setUrl(url);
        fileMessageDao.addFileMessage(fm);
    }

    public static void main(String[] args) {
        HtmlComtentParser hp = new HtmlComtentParser();

        hp.run("www.baidu.com");
    }

}
