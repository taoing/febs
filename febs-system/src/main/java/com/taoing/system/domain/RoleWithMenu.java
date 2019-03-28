package com.taoing.system.domain;

import java.util.List;

public class RoleWithMenu extends Role {

    private static final long serialVersionUID = -7032022597960889769L;

    private Long menuId;

    private List<Long> menuIds;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }
}
