package com.taoing.quartz.domain;

import com.taoing.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_job_log")
public class JobLog implements Serializable {

    private static final long serialVersionUID = -7228797724216258618L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "LOG_ID")
    private Long logId;

    @Column(name = "JOB_ID")
    @ExportConfig("任务ID")
    private Long jobId;

    @Column(name = "BEAN_NAME")
    @ExportConfig(value = "Bean名称")
    private String beanName;

    @Column(name = "METHOD_NAME")
    @ExportConfig(value = "方法名称")
    private String methodName;

    @Column(name = "PARAMS")
    @ExportConfig(value = "参数")
    private String params;

    @Column(name = "STATUS")
    @ExportConfig(value = "状态", convert = "s:0=成功,1=失败")
    private String status;

    @Column(name = "ERROR")
    @ExportConfig(value = "异常信息")
    private String error;

    @Column(name = "TIMES")
    @ExportConfig(value = "耗时(毫秒)")
    private Long times;

    @Column(name = "CREATE_TIME")
    @ExportConfig(value = "创建时间", convert = "c:com.taoing.common.utils.poi.convert.TimeConvert")
    private Date createTime;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName == null ? null : beanName.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
