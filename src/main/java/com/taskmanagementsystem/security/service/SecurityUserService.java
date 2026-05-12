package com.taskmanagementsystem.security.service;

import com.taskmanagementsystem.entity.util.RoleName;

public interface SecurityUserService {

    RoleName getRoleNameByEmail(String email);
}
