package com.taoing.security.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoing.common.domain.FebsConstant;
import com.taoing.security.exception.FebsCredentialException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    private String openId;

    private String appId;

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(FebsConstant.GET_QQ_OPEN_ID_URL, accessToken);
        String result = this.getRestTemplate().getForObject(url, String.class);

        log.info(result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(FebsConstant.GET_QQ_USER_INFO_URL, this.appId, this.openId);
        String result = this.getRestTemplate().getForObject(url, String.class);
        log.info(result);
        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FebsCredentialException("获取QQ用户信息失败!");
        }
    }
}
