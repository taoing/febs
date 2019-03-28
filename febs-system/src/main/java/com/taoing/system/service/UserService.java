package com.taoing.system.service;

import com.taoing.common.service.IService;
import com.taoing.system.domain.MyUser;
import com.taoing.system.domain.UserWithRole;

import java.io.IOException;
import java.util.List;

public interface UserService extends IService<MyUser> {

    MyUser findByNameOrMobile(String username);

    UserWithRole findById(Long userId);

    MyUser findByName(String username);

    List<MyUser> findUserWithDept(MyUser user);

    void registerUser(MyUser user);

    void updateTheme(String theme, String username);

    void addUser(MyUser user, Long[] roles);

    void updateUser(MyUser user, Long[] roles);

    void deleteUsers(String userIds);

    void updateLoginTime(String username);

    void updatePassword(String password, String username) throws IOException;

    MyUser findUserProfile(MyUser user);

    void updateUserProfile(MyUser user);

    void mobileBind(String username, String mobile);

    void mobileUnbind(String username, String mobile);
}
