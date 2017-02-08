package com.zhiwen.crawler.site;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by zhengwenzhu on 2017/2/8.
 */
public class SimpleHandler extends AbstractHandler {

    @Override
    public void handle(String target, Request baseRequest,
                       HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println(generateRandomPage());
        baseRequest.setHandled(true);

        //simulate network block for 0-2000ms
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            //ignore
        }
    }

    private String generateRandomPage() {
        StringBuilder sb = new StringBuilder(2048);
        int line = new Random().nextInt(100);
        for (int i = 0; i < line; i ++) {
            sb.append(genRandomLine());
        }
        return sb.toString();
    }

    private String genRandomLine() {
        return new Random().nextBoolean() ? text(): href();
    }

    private String href() {
        return String.format("<a href=%s>%s</a>", randomUri(), text());
    }

    private String randomUri() {

        return String.format("/%d/%d?parm=%d", new Random().nextInt(1000), new Random().nextInt(100000), new Random().nextInt(1000000));
    }

    private String text() {
        return "Hello, world! </br>";
    }
}
