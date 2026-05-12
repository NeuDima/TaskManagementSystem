package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.UserShortDto;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.UserShortMapper;
import org.springframework.stereotype.Component;

@Component
public class UserShortMapperImpl implements UserShortMapper {

    @Override
    public UserShortDto map(User user) {
        return new UserShortDto(
                user.getId(),
                user.getEmail()
        );
    }
}
