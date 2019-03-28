package com.taoing.system.service;

import com.taoing.common.service.IService;
import com.taoing.system.domain.UserRole;

public interface UserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String roleIds);

    void deleteUserRolesByUserId(String userIds);
}
