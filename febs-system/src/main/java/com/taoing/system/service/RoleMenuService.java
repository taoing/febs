package com.taoing.system.service;

import com.taoing.common.service.IService;
import com.taoing.system.domain.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenusByRoleId(String roleIds);

    void deleteRoleMenusByMenuId(String menuIds);
}
