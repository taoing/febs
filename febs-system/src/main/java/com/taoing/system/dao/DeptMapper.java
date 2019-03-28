package com.taoing.system.dao;

import com.taoing.common.config.MyMapper;
import com.taoing.system.domain.Dept;

import java.util.List;

public interface DeptMapper extends MyMapper<Dept> {

    // 删除父节点, 子节点变成顶级节点(根据实际业务调整)
    void changeToTop(List<String> deptIds);
}
