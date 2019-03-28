package com.taoing.system.domain;

import com.taoing.common.annotation.ExportConfig;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 983843476295253613L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    @ExportConfig(value = "操作用户")
    private String username;

    @Column(name = "OPERATION")
    @ExportConfig(value = "描述")
    private String operation;

    @Column(name = "TIME")
    @ExportConfig(value = "耗时(毫秒)")
    private Long time;

    @Column(name = "METHOD")
    @ExportConfig(value = "操作方法")
    private String method;

    @Column(name = "PARAMS")
    @ExportConfig(value = "参数")
    private String params;

    @Column(name = "IP")
    @ExportConfig(value = "IP地址")
    private String ip;

    @Column(name = "CREATE_TIME")
    @ExportConfig(
            value = "操作时间",
            convert = "c:com.taoing.common.utils.poi.convert.TimeConvert"
    )
    private Date createTime;

    @Column(name = "LOCATION")
    @ExportConfig(value = "地点")
    private String location;

    // 用于搜索条件中的时间字段
    @Transient
    private String timeField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeField() {
        return timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }
}
