package com.taoing.system.domain;

import com.taoing.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 6856234579102902549L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME")
    @ExportConfig(value = "角色")
    private String roleName;

    @Column(name = "REMARK")
    @ExportConfig(value = "描述")
    private String remark;

    @Column(name = "CREATE_TIME")
    @ExportConfig(
            value = "创建时间",
            convert = "c:com.taoing.common.utils.poi.convert.TimeConvert"
    )
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}
