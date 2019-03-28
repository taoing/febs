package com.taoing.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Febs工具类
 */
public class FebsUtil {

    private FebsUtil() {

    }

    /**
     * 简单判断是否为手机号
     */
    public static boolean isPhoneNo(String phoneNo) {
        String regex = "1\\d{10}";
        if (StringUtils.isBlank(phoneNo))
            return false;
        else
            return phoneNo.matches(regex);
    }

    /**
     * 判断是否为AJAX请求
     *
     * @param request HttpServletRequest
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }
}
