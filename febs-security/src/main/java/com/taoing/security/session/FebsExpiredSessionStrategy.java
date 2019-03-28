package com.taoing.security.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoing.common.domain.FebsConstant;
import com.taoing.common.domain.ResponseBo;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 处理session过期
 * 导致session过期的原因:
 * 1. 并发登录控制
 * 2. 被踢出
 *
 * 相当于监听器一样
 */
public class FebsExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().setContentType(FebsConstant.JSON_UTF8);
        event.getResponse().getWriter().write(mapper.writeValueAsString(ResponseBo.unAuthorized("登录已失效")));
    }
}
