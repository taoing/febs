package com.taoing.system.service.impl;

import com.taoing.common.service.impl.BaseService;
import com.taoing.system.dao.UserMapper;
import com.taoing.system.dao.UserRoleMapper;
import com.taoing.system.domain.MyUser;
import com.taoing.system.domain.UserRole;
import com.taoing.system.domain.UserWithRole;
import com.taoing.system.service.UserRoleService;
import com.taoing.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.taoing.common.utils.FebsUtil.isPhoneNo;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseService<MyUser> implements UserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public MyUser findByNameOrMobile(String var) {
        Example example = new Example(MyUser.class);
        boolean isMobile = isPhoneNo(var);
        if (isMobile) {
            example.createCriteria().andCondition("mobile=", var);
        } else {
            example.createCriteria().andCondition("lower(username)=", var.toLowerCase());
        }
        List<MyUser> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public MyUser findByName(String username) {
        Example example = new Example(MyUser.class);
        example.createCriteria().andCondition("lower(username)=", username.toLowerCase());
        List<MyUser> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<MyUser> findUserWithDept(MyUser user) {
        try {
            return this.userMapper.findUserWithDept(user);
        } catch (Exception e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void registerUser(MyUser user) {
        user.setCreateTime(new Date());
        user.setTheme(MyUser.DEFAULT_THEME);
        user.setAvatar(MyUser.DEFAULT_AVATAR);
        user.setSsex(MyUser.SEX_UNKNOWN);
        this.save(user);
        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(2L);
        this.userRoleMapper.insert(ur);
    }

    @Override
    @Transactional
    public void updateTheme(String theme, String username) {
        Example example = new Example(MyUser.class);
        example.createCriteria().andCondition("username=", username);
        MyUser user = new MyUser();
        user.setTheme(theme);
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void addUser(MyUser user, Long[] roles) {
        user.setCreateTime(new Date());
        user.setTheme(MyUser.DEFAULT_THEME);
        user.setAvatar(MyUser.DEFAULT_AVATAR);
        this.save(user);
        setUserRoles(user, roles);
    }

    private void setUserRoles(MyUser user, Long[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            this.userRoleMapper.insert(ur);
        });
    }

    @Override
    @Transactional
    public void updateUser(MyUser user, Long[] roles) {
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("user_id=", user.getUserId());
        // 更新多对多关系: 先删除再保存
        this.userRoleMapper.deleteByExample(example);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String userIds) {
        List<String> list = Arrays.asList(userIds.split(","));
        this.batchDelete(list, "userId", MyUser.class);
        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    @Override
    @Transactional
    public void updateLoginTime(String username) {
        Example example = new Example(MyUser.class);
        example.createCriteria().andCondition("lower(username)=", username.toLowerCase());
        MyUser user = new MyUser();
        user.setLastLoginTime(new Date());
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void updatePassword(String password, String username) {
        Example example = new Example(MyUser.class);
        example.createCriteria().andCondition("username=", username);
        MyUser user = new MyUser();
        user.setPassword(password);
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public UserWithRole findById(Long userId) {
        List<UserWithRole> list = this.userMapper.findUserWithRole(userId);
        List<Long> roleList = new ArrayList<>();
        for (UserWithRole uwr : list) {
            roleList.add(uwr.getRoleId());
        }
        if (list.isEmpty()) {
            return null;
        }
        UserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleList);
        return userWithRole;
    }

    @Override
    public MyUser findUserProfile(MyUser user) {
        return this.userMapper.findUserProfile(user);
    }

    @Override
    @Transactional
    public void updateUserProfile(MyUser user) {
        user.setUsername(null);
        user.setPassword(null);
        if (user.getDeptId() == null)
            user.setDeptId(0L);
        this.updateNotNull(user);
    }

    @Override
    @Transactional
    public void mobileBind(String username, String mobile) {
        Example example = new Example(MyUser.class);
        example.createCriteria().andCondition("username=", username);

        MyUser user = new MyUser();
        user.setMobile(mobile);
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public void mobileUnbind(String username, String mobile) {
        Example example = new Example(MyUser.class);
        example.createCriteria().andCondition("username=", username);

        MyUser user = new MyUser();
        user.setMobile("");
        this.userMapper.updateByExampleSelective(user, example);
    }
}
