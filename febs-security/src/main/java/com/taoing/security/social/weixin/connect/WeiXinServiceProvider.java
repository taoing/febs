package com.taoing.security.social.weixin.connect;

import com.taoing.common.domain.FebsConstant;
import com.taoing.security.social.weixin.api.WeiXin;
import com.taoing.security.social.weixin.api.WeiXinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class WeiXinServiceProvider extends AbstractOAuth2ServiceProvider<WeiXin> {

    public WeiXinServiceProvider(String appId, String appSecret) {
        super(new WeiXinOAuth2Template(appId, appSecret, FebsConstant.WEIXIN_AUTHORIZE_URL,
                FebsConstant.GET_WEIXIN_ACCESSTOKEN_URL));
    }

    @Override
    public WeiXin getApi(String accessToken) {
        return new WeiXinImpl(accessToken);
    }
}
