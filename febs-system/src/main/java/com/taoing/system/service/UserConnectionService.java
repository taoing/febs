package com.taoing.system.service;

import com.taoing.common.service.IService;
import com.taoing.system.domain.UserConnection;

import java.util.List;

public interface UserConnectionService extends IService<UserConnection> {

    boolean isExist(String userId, String providerId);

    List<UserConnection> findByProviderUserId(String providerUserId);

    void delete(UserConnection userConnection);
}
