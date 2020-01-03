package com.fangdd.tp.core.config.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * @author xuwenzhen
 * @date 18/8/23
 */
public class DuoHttpLogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DuoHttpLogFilter.class);
    private static final String STR_POST = "POST";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = new MultiReadHttpServletRequest((HttpServletRequest) request);
        long startTime = System.currentTimeMillis();
        String method = req.getMethod();

        String headers = headerToString(req);
        String uri = req.getRequestURI();
        String queryString = req.getQueryString();
        if (STR_POST.equalsIgnoreCase(method)) {
            String body = requestBodyToString(req);
            logger.info("{} {} query:{} head:{} body:{}", method, uri, queryString, headers, body);
        } else {
            logger.info("{} {} query:{} head:{}", method, uri, queryString, headers);
        }

        try {
            chain.doFilter(req, response);
        } finally {
            long t = System.currentTimeMillis() - startTime;
            String slowApi = t > 800 ? " 慢接口" : "-";
            logger.info("{} {} {} 耗时 {}", slowApi, method, uri, t);
        }
    }

    private String requestBodyToString(ServletRequest request) {
        BufferedReader reader;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            logger.warn("读取requestBody失败！", e);
            return null;
        }
        return reader.lines().collect(Collectors.joining());
    }

    @Override
    public void destroy() {

    }

    private String headerToString(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            sb
                    .append(header)
                    .append(":")
                    .append(request.getHeader(header))
                    .append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}