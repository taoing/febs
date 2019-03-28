package com.taoing.system.service;

import com.taoing.common.service.IService;
import com.taoing.system.domain.SysLog;

import java.util.List;

public interface LogService extends IService<SysLog> {

    List<SysLog> findAllLogs(SysLog log);

    void deleteLogs(String logIds);
}
