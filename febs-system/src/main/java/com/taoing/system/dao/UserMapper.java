package com.taoing.system.dao;

import com.taoing.common.config.MyMapper;
import com.taoing.system.domain.MyUser;
import com.taoing.system.domain.UserWithRole;

import java.util.List;

public interface UserMapper extends MyMapper<MyUser> {

    List<MyUser> findUserWithDept(MyUser user);

    List<UserWithRole> findUserWithRole(Long userId);

    MyUser findUserProfile(MyUser user);
}
