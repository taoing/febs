package com.taoing.web.controller.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taoing.common.domain.QueryRequest;
import com.taoing.security.domain.FebsSocialUserDetails;
import com.taoing.security.domain.FebsUserDetails;
import com.taoing.system.domain.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseController {

    private Map<String, Object> getDataTable(PageInfo<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getList());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
    }

    protected Map<String, Object> selectByPageNumSize(QueryRequest request, Supplier<?> s) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>((List<?>) s.get());
        PageHelper.clearPage();
        return getDataTable(pageInfo);
    }

    protected MyUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        MyUser user = new MyUser();
        if (principal instanceof FebsUserDetails) {
            FebsUserDetails userDetails = (FebsUserDetails) principal;
            user.setUserId(userDetails.getUserId());
            user.setPassword(userDetails.getPassword());
            user.setUsername(userDetails.getUsername());
        } else {
            FebsSocialUserDetails userDetails = (FebsSocialUserDetails) principal;
            user.setUserId(userDetails.getUsersId());
            user.setPassword(userDetails.getPassword());
            user.setUsername(userDetails.getUsername());
        }
        return user;
    }
}
