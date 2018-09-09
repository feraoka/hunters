package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.User;
import com.hunterstudios.hunters.entity.UserRole;
import com.hunterstudios.hunters.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    @NonNull
    private UserRepository userRepository;

    UserRole getUserRole(String account) {
        UserRole userRole = new UserRole();
        User user = userRepository.select(account);
        if (user == null) {
            return userRole;
        }
        userRole.setAccount(user.getAccount());
        userRole.setPassword(user.getPassword());
        String[] role = { "ROLE_ADMIN" }; // it should be ok, unless we need other roles
        userRole.setRoles(role);
        return userRole;
    }
}
