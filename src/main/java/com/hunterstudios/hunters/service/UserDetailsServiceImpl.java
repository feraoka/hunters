package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.UserRole;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetailsServiceの実装クラス
 * Spring Securityでのユーザー認証に使用する
 */
@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @NonNull
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String loginId)
            throws UsernameNotFoundException {

        UserRole userRole = userService.getUserRole(loginId);
        return new User(userRole.getAccount(), userRole.getPassword(),
                AuthorityUtils.createAuthorityList(userRole.getRoles()));
    }

}