package com.taoing.web.domain;

import com.taoing.security.domain.LoginType;

import java.io.Serializable;

public class UserOnLine implements Serializable {

    private static final long serialVersionUID = -7092712363944560528L;

    private String username;

    private String sessionId;

    private String loginTime;

    private String lastRequested;

    private String ip;

    private String location;

    private LoginType loginType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastRequested() {
        return lastRequested;
    }

    public void setLastRequested(String lastRequested) {
        this.lastRequested = lastRequested;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
