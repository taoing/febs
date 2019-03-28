package com.taoing.system.service;

import com.taoing.common.domain.Tree;
import com.taoing.common.service.IService;
import com.taoing.system.domain.Dept;

import java.util.List;

public interface DeptService extends IService<Dept> {

    Tree<Dept> getDeptTree();

    List<Dept> findAllDepts(Dept dept);

    Dept findByName(String deptName);

    Dept findById(Long deptId);

    void addDept(Dept dept);

    void updateDept(Dept dept);

    void deleteDepts(String deptIds);
}
