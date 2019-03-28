package com.taoing.system.domain;

import java.util.List;

public class UserWithRole extends MyUser {

    private static final long serialVersionUID = 4043443821245082553L;

    private Long roleId;

    private List<Long> roleIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
