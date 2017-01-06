package com.zhiwen.crawler.file.match;

import com.zhiwen.crawler.file.store.model.FileMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

/**
 * Created by zhiwenzhu on 17/1/6.
 * 该类用来匹配Html  <head></head> 标签中的有用的内容
 */
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
            String title = s.substring(7, s.length() - 8);
            fm.setTitle(title);
        }

        if (keywordsMatcher.find()) {
            String s = keywordsMatcher.group();
            String keywords = s.substring(25, s.length() - 3);
            if (StringUtils.isNotBlank(keywords) && keywords.length() > 200) {
                keywords = keywords.substring(0, 199);
            }
            fm.setKeywords(keywords);
        }

        if (descriptionMatcher.find()) {
            String s = descriptionMatcher.group();
            String des = s.substring(28, s.length() - 3);
            if (StringUtils.isNotBlank(des) && des.length() > 200) {
                des = des.substring(0, 199);
            }
            fm.setDescription(des);
        }

        return fm;

    }


}
