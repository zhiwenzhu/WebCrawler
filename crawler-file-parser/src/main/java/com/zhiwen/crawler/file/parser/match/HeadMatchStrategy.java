package com.zhiwen.crawler.file.parser.match;

import com.zhiwen.crawler.file.store.model.FileMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

/**
 * Created by zhiwenzhu on 17/1/6.
 * 该类用来匹配Html  <head></head> 标签中的有用的内容
 */
@Deprecated
public class HeadMatchStrategy {
    //匹配html页面标题的正则；
    private static final String TITLE_REGEX = "<title>.*</title>";

    //匹配html中keywords属性内容；
    private static final String KEYWORDS_REGEX = "name=\"Keywords\" content=\"[^>]*";

    //匹配html中description属性内容；
    private static final String DESCRIPTION_REGEX = "name=\"Description\" content=\"[^>]*";

    //匹配<head></head>标签中的内容
    private static final String HEAD_REGEX = "<head>.*</head>";

    public static FileMessage getMessageFromPageContent(String pageContent) {
        FileMessage fm = new FileMessage();
        String head = MatchUtil.preHandlePageContent(pageContent, HEAD_REGEX);
        Matcher titleMatcher = MatchUtil.getMatcher(head, TITLE_REGEX);
        Matcher keywordsMatcher = MatchUtil.getMatcher(head, KEYWORDS_REGEX);
        Matcher descriptionMatcher = MatchUtil.getMatcher(head, DESCRIPTION_REGEX);

        if (titleMatcher.find()) {
            String s = titleMatcher.group();
            System.out.println("Title:" + s);
            String title = s.substring(7, s.length() - 8);
            fm.setTitle(title);
        }

        if (keywordsMatcher.find()) {
            String s = keywordsMatcher.group();
            System.out.println("Keywords:" + s);
            String keywords = s.length() <= 28 ? null : s.substring(25, s.length() - 3);
            if (StringUtils.isNotBlank(keywords) && keywords.length() > 200) {
                keywords = keywords.substring(0, 199);
            }
            fm.setKeywords(keywords);
        }

        if (descriptionMatcher.find()) {
            String s = descriptionMatcher.group();
            System.out.println("Description:" + s);
            String des = s.length() <= 31 ? null : s.substring(28, s.length() - 3);
            if (StringUtils.isNotBlank(des) && des.length() > 200) {
                des = des.substring(0, 199);
            }
            fm.setDescription(des);
        }

        if (fm.getDescription() == null && fm.getKeywords() == null) {
            return null;
        }

        return fm;

    }


}
