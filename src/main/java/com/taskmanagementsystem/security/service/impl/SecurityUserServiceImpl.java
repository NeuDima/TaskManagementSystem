package com.taskmanagementsystem.security.service.impl;

import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.entity.util.RoleName;
import com.taskmanagementsystem.repository.UserRepository;
import com.taskmanagementsystem.security.service.SecurityUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {

    private final UserRepository userRepository;

    @Override
    public RoleName getRoleNameByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return user.getRole().getName();
    }
}
