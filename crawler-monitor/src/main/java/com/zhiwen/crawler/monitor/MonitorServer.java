package com.zhiwen.crawler.monitor;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by zhengwenzhu on 2017/2/9.
 */
public class MonitorServer {

    private static Server server;

    private static final int SERVER_PORT = 4098;

    private Model model;

    public MonitorServer(Model model) {
        this.model = model;
    }

    public void start() {
        if (MonitorServer.server == null) {
            MonitorServer.server = new Server(SERVER_PORT);
            MonitorServer.server.setHandler(new MonitorServerHandler(model));
        }

        if (!MonitorServer.server.isStarted()) {
            try {
                MonitorServer.server.start();
                System.err.println("Server started at " + MonitorServer.server.getURI());
            } catch (Exception e) {
                System.err.println("Monitor server start fail: " + e.getMessage());
            }
        } else {
            System.err.println("Monitor server already started at " + MonitorServer.server.getURI());
        }
    }

    private static class MonitorServerHandler extends AbstractHandler {

        private static final java.lang.String TEMPLATE_PATH = "monitor.ftl";

        private Model model;

        private Configuration configuration;

        public MonitorServerHandler(Model model) {
            this.model = model;
            init();
        }

        @Override
        public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            Template t = configuration.getTemplate(TEMPLATE_PATH);
            StringWriter sw = new StringWriter(5000);
            try {
                t.process(model, sw);
                response.setContentType("text/html; charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.println(sw.toString());
                baseRequest.setHandled(true);
            } catch (TemplateException e) {
                throw new ServletException(e);
            }

        }

        public void init() {
            configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(this.getClass(), "/ftl");
        }
    }
}
