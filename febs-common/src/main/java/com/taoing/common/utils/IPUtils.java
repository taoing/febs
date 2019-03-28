package com.taoing.common.utils;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {

    private static final String UNKNOWN = "unknown";

    protected IPUtils() {

    }

    /**
     * 获取ip地址
     * 使用Nginx等方向代理软件, 不能通过request.getRemoteAddr()获取ip地址
     * 如果使用了多级反向代理, X-Forwarded-For的值并不止一个, 而是一串地址,
     * X-Forwarded-For中第一个非unknown的有效ip字符串, 则为真实ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
