package com.taoing.system.service;

import com.taoing.common.domain.Tree;
import com.taoing.common.service.IService;
import com.taoing.system.domain.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "menuService")
public interface MenuService extends IService<Menu> {

    String findUserPermissions(String userName);

    List<Menu> findUserMenus(String userName);

    List<Menu> findAllMenus(Menu menu);

    Tree<Menu> getMenuButtonTree();

    Tree<Menu> getMenuTree();

    Tree<Menu> getUserMenu(String userName);

    Menu findById(Long menuId);

    Menu findByNameAndType(String menuName, String type);

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenus(String menuIds);

    @Cacheable(key = "'url_' + #p0")
    List<Map<String, String>> getAllUrl(String p1);
}
