package com.taoing.security.properties;

public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private String socialRedirectUrl = "";

    private String socialBindUrl = "";

    private String socialRegisterUrl = "";

    private QQProperties qq = new QQProperties();

    private WeiXinProperties weixin = new WeiXinProperties();

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public String getSocialRedirectUrl() {
        return socialRedirectUrl;
    }

    public void setSocialRedirectUrl(String socialRedirectUrl) {
        this.socialRedirectUrl = socialRedirectUrl;
    }

    public String getSocialBindUrl() {
        return socialBindUrl;
    }

    public void setSocialBindUrl(String socialBindUrl) {
        this.socialBindUrl = socialBindUrl;
    }

    public String getSocialRegisterUrl() {
        return socialRegisterUrl;
    }

    public void setSocialRegisterUrl(String socialRegisterUrl) {
        this.socialRegisterUrl = socialRegisterUrl;
    }

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WeiXinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeiXinProperties weixin) {
        this.weixin = weixin;
    }
}
