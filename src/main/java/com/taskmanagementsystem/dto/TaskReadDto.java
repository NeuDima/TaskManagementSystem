package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.entity.util.Priority;
import com.taskmanagementsystem.entity.util.Status;

import java.util.List;

public record TaskReadDto(
        Integer id,
        String title,
        String description,
        Status status,
        Priority priority,

        UserShortDto author,
        UserShortDto executor,

        List<CommentShortDto> comments
) {
}
