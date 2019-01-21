package com.fangdd.tp.core.config.http;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auth ycoe
 * @date 18/11/28
 */
public class HttpFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HttpFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MultiReadHttpServletRequest req = new MultiReadHttpServletRequest((HttpServletRequest) request);
        long startTime = System.currentTimeMillis();
        String method = req.getMethod();
        String headers = headerToString(req);
        String uri = req.getRequestURI();
        String queryString = req.getQueryString();
        if ("POST".equalsIgnoreCase(method)) {
            String t = requestBodyToString(req);
            logger.info("{} {} query:{} head:{} body:{}", method, uri, queryString, headers, t);
        } else {
            logger.info("{} {} query:{} head:{}", method, uri, queryString, headers);
        }

        boolean success = false;
        try {
            chain.doFilter(req, response);
        } finally {
            long t1 = System.currentTimeMillis() - startTime;
            String slowApi1 = t1 > 800L ? " 慢接口" : "-";
            logger.info("{} {} {} 耗时 {}", slowApi1, method, uri, t1);
        }
    }

    @Override
    public void destroy() {
        //do nothing
    }

    private String headerToString(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return "";
        }
        Map<String, Object> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headerMap.put(header, request.getHeader(header));
        }
        return JSONObject.toJSONString(headerMap);
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
}
