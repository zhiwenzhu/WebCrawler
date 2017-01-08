package com.zhiwen.crawler.file.parser.match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class MatchUtil {
    //匹配<body></body> 标签中的内容；
    private static final String BODY_REGEX = "<body.*</body>";

    //匹配<head></head>标签中的内容
    private static final String HEAD_REGEX = "<head>.*</head>";

    public static Matcher getMatcher(String pageContent, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pageContent);
        return matcher;
    }

    public static String preHandlePageContent(String pageContent, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pageContent);

        String result = matcher.find()? matcher.group() : "";
        return result;
    }
}
