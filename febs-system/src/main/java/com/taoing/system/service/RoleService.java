package com.taoing.system.service;

import com.taoing.common.service.IService;
import com.taoing.system.domain.Role;
import com.taoing.system.domain.RoleWithMenu;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> findUserRole(String userName);

    List<Role> findAllRole(Role role);

    RoleWithMenu findRoleWithMenus(Long roleId);

    Role findByName(String roleName);

    void addRole(Role role, Long[] menuIds);

    void updateRole(Role role, Long[] menuIds);

    void deleteRoles(String roleIds);
}
