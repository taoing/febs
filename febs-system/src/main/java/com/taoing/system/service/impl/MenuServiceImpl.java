package com.taoing.system.service.impl;

import com.taoing.common.domain.Tree;
import com.taoing.common.service.impl.BaseService;
import com.taoing.common.utils.TreeUtils;
import com.taoing.system.dao.MenuMapper;
import com.taoing.system.domain.Menu;
import com.taoing.system.service.MenuService;
import com.taoing.system.service.RoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private WebApplicationContext applicationContext;

    @Override
    public String findUserPermissions(String userName) {
        List<Menu> list = this.menuMapper.findUserPermissions(userName);
        return list.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }

    @Override
    public List<Menu> findUserMenus(String userName) {
        return this.menuMapper.findUserMenus(userName);
    }

    @Override
    public List<Menu> findAllMenus(Menu menu) {
        try {
            Example example = new Example(Menu.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(menu.getMenuName())) {
                criteria.andCondition("menu_name=", menu.getMenuName());
            }
            if (StringUtils.isNotBlank(menu.getType())) {
                criteria.andCondition("type=", Long.valueOf(menu.getType()));
            }
            example.setOrderByClause("menu_id");
            return this.selectByExample(example);
        } catch (NumberFormatException e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Tree<Menu> getMenuButtonTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = this.findAllMenus(new Menu());
        buildTrees(trees, menus);
        return TreeUtils.build(trees);
    }

    private void buildTrees(List<Tree<Menu>> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            trees.add(tree);
        });
    }

    @Override
    public Tree<Menu> getMenuTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        Example example = new Example(Menu.class);
        example.createCriteria().andCondition("type=", 0);
        example.setOrderByClause("create_time");
        List<Menu> menus = this.selectByExample(example);
        buildTrees(trees, menus);
        return TreeUtils.build(trees);
    }

    @Override
    public Tree<Menu> getUserMenu(String userName) {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = this.findUserMenus(userName);
        menus.forEach(menu -> {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setUrl(menu.getUrl());
            trees.add(tree);
        });
        return TreeUtils.build(trees);
    }

    @Override
    public Menu findByNameAndType(String menuName, String type) {
        Example example = new Example(Menu.class);
        example.createCriteria().andCondition("lower(menu_name)=", menuName.toLowerCase())
                .andEqualTo("type", Long.valueOf(type));
        List<Menu> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @Transactional
    public void addMenu(Menu menu) {
        menu.setCreateTime(new Date());
        if (menu.getParentId() == null)
            menu.setParentId(0L);
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
        this.save(menu);
    }

    @Override
    @Transactional
    public void deleteMenus(String menuIds) {
        List<String> list = Arrays.asList(menuIds.split(","));
        this.batchDelete(list, "menuId", Menu.class);
        this.roleMenuService.deleteRoleMenusByMenuId(menuIds);
        this.menuMapper.changeToTop(list);
    }

    @Override
    public Menu findById(Long menuId) {
        return this.selectByKey(menuId);
    }

    @Override
    @Transactional
    public void updateMenu(Menu menu) {
        menu.setModifyTime(new Date());
        if (menu.getParentId() == null)
            menu.setParentId(0L);
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setUrl(null);
            menu.setIcon(null);
        }
        this.updateNotNull(menu);
    }

    @Override
    public List<Map<String, String>> getAllUrl(String p1) {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(
                RequestMappingHandlerMapping.class);
        // 获取url与Controller类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> urlList = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            PreAuthorize permissions = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            String perms = "";
            if (null != permissions) {
                String value = permissions.value();
                value = StringUtils.substringBetween(value, "hasAuthority(", ")");
                perms = StringUtils.join(value, ",");
            }
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put("url", url.replaceFirst("\\/", ""));
                urlMap.put("perms", perms);
                urlList.add(urlMap);
            }
        }
        return urlList;
    }
}
