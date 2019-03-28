package com.taoing.security.service;

import com.taoing.common.utils.DateUtil;
import com.taoing.security.domain.FebsSocialUserDetails;
import com.taoing.security.domain.FebsUserDetails;
import com.taoing.security.domain.LoginType;
import com.taoing.security.exception.FebsCredentialException;
import com.taoing.system.domain.MyUser;
import com.taoing.system.service.MenuService;
import com.taoing.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.taoing.common.utils.FebsUtil.isPhoneNo;

@Service
public class FebsUserDetailService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = this.userService.findByNameOrMobile(username);
        boolean isMobile = isPhoneNo(username);
        if (user != null) {
            String permissions = this.menuService.findUserPermissions(user.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(MyUser.STATUS_VALID, user.getStatus()))
                notLocked = true;
            FebsUserDetails userDetails = new FebsUserDetails(user.getUsername(), user.getPassword(), true, true, true,
                    notLocked, AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
            userDetails.setTheme(user.getTheme());
            userDetails.setAvatar(user.getAvatar());
            userDetails.setEmail(user.getEmail());
            userDetails.setMobile(user.getMobile());
            userDetails.setSsex(user.getSsex());
            userDetails.setUserId(user.getUserId());
            userDetails.setPassword(user.getPassword());
            userDetails.setLoginTime(DateUtil.getDateFormat(new Date(), DateUtil.FULL_DATE_FORMAT));
            if (isMobile)
                userDetails.setLoginType(LoginType.sms);
            return userDetails;
        } else {
            if (isMobile) throw new FebsCredentialException("该手机号还未绑定账号!");
            throw new UsernameNotFoundException("");
        }
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        MyUser user = this.userService.findByNameOrMobile(userId);
        if (user != null) {
            String permissions = this.menuService.findUserPermissions(user.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(MyUser.STATUS_VALID, user.getStatus()))
                notLocked = true;
            FebsSocialUserDetails userDetails = new FebsSocialUserDetails(user.getUsername(), user.getPassword(),
                    true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
            userDetails.setTheme(user.getTheme());
            userDetails.setAvatar(user.getAvatar());
            userDetails.setEmail(user.getEmail());
            userDetails.setMobile(user.getMobile());
            userDetails.setSsex(user.getSsex());
            userDetails.setUsersId(user.getUserId());
            userDetails.setPassword(user.getPassword());
            userDetails.setLoginTime(DateUtil.getDateFormat(new Date(), DateUtil.FULL_DATE_FORMAT));
            return userDetails;
        } else {
            return null;
        }
    }
}
