package com.taoing.system.domain;

import com.google.common.base.MoreObjects;
import com.taoing.common.annotation.ExportConfig;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_user")
public class MyUser implements Serializable {

    private static final long serialVersionUID = 4538859477161000180L;

    /**
     * 账户状态
     */
    public static final String STATUS_VALID = "1";
    public static final String STATUS_LOCK = "0";
    public static final String DEFAULT_THEME = "green";
    public static final String DEFAULT_AVATAR = "default.jpg";

    /**
     * 性别
     */
    public static final String SEX_MALE = "0";
    public static final String SEX_FEMALE = "1";
    public static final String SEX_UNKNOWN = "2";

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USERNAME")
    @ExportConfig(value = "用户名")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DEPT_ID")
    private Long deptId;

    /**
     * @Transient 注解的field不作为数据库的字段列
     */
    @ExportConfig(value = "部门")
    @Transient
    private String deptName;

    @Column(name = "EMAIL")
    @ExportConfig(value = "邮箱")
    private String email;

    @Column(name = "MOBILE")
    @ExportConfig(value = "手机")
    private String mobile;

    @Column(name = "STATUS")
    @ExportConfig(value = "状态", convert = "s:0=锁定,1=有效")
    private String status = STATUS_VALID;

    @ExportConfig(
            value = "创建时间",
            convert = "c:com.taoing.common.utils.poi.convert.TimeConvert"
    )
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;

    @ExportConfig(value = "性别", convert = "s:0=男,1=女,2=保密")
    @Column(name = "SSEX")
    private String ssex;

    @ExportConfig(value = "主题")
    @Column(name = "THEME")
    private String theme;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "DESCRIPTION")
    private String description;

    @Transient
    private String roleName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex == null ? null : ssex.trim();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("username", username)
                .add("password", password)
                .add("deptId", deptId)
                .add("deptName", deptName)
                .add("email", email)
                .add("mobile", mobile)
                .add("status", status)
                .add("createTime", createTime)
                .add("modifyTime", modifyTime)
                .add("lastLoginTime", lastLoginTime)
                .add("ssex", ssex)
                .add("theme", theme)
                .add("avatar", avatar)
                .add("description", description)
                .add("roleName", roleName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MyUser && StringUtils.equals(username, ((MyUser) o).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
