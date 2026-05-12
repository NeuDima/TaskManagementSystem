package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.UserShortDto;
import com.taskmanagementsystem.entity.User;

public interface UserShortMapper {

    UserShortDto map(User user);
}
