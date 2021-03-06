package com.taoing.system.service.impl;

import com.taoing.common.service.impl.BaseService;
import com.taoing.system.domain.RoleMenu;
import com.taoing.system.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("roleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends BaseService<RoleMenu> implements RoleMenuService {

    @Override
    @Transactional
    public void deleteRoleMenusByRoleId(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(","));
        this.batchDelete(list, "roleId", RoleMenu.class);
    }

    @Override
    @Transactional
    public void deleteRoleMenusByMenuId(String menuIds) {
        List<String> list = Arrays.asList(menuIds.split(","));
        this.batchDelete(list, "menuId", RoleMenu.class);
    }
}
