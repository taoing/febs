package com.taoing.quartz.service;

import com.taoing.common.service.IService;
import com.taoing.quartz.domain.JobLog;

import java.util.List;

public interface JobLogService extends IService<JobLog> {

    List<JobLog> findAllJobLogs(JobLog jobLog);

    void saveJobLog(JobLog log);

    void deleteBatch(String jobLogIds);
}
