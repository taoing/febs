package com.taoing.web.test;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
        System.out.println("myfilter init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // do nothing
        System.out.println("myfilter doing...");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
