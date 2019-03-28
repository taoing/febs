package com.taoing.system.dao;

import com.taoing.common.config.MyMapper;
import com.taoing.system.domain.Role;
import com.taoing.system.domain.RoleWithMenu;

import java.util.List;

public interface RoleMapper extends MyMapper<Role> {

    List<Role> findUserRole(String userName);

    List<RoleWithMenu> findById(Long roleId);
}
