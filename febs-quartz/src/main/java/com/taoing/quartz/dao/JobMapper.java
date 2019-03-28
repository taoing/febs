package com.taoing.quartz.dao;

import com.taoing.common.config.MyMapper;
import com.taoing.quartz.domain.Job;

import java.util.List;

public interface JobMapper extends MyMapper<Job> {

    List<Job> queryList();
}
