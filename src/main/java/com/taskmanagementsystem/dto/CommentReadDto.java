package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;

public record CommentReadDto(
        Integer id,
        User user,
        Task task,
        String text
) {
}
