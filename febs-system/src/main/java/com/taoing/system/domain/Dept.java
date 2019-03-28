package com.taoing.system.domain;

import com.taoing.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = -2986950333724799016L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "DEPT_ID")
    @ExportConfig(value = "编号")
    private Long deptId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "DEPT_NAME")
    @ExportConfig(value = "部门名称")
    private String deptName;

    @Column(name = "ORDER_NUM")
    private Long orderNum;

    @Column(name = "CREATE_TIME")
    @ExportConfig(
            value = "创建时间",
            convert = "c:com.taoing.common.utils.poi.convert.TimeConvert"
    )
    private Date createTime;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
