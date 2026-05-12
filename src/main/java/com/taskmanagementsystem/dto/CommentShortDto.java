package com.taskmanagementsystem.dto;

public record CommentShortDto(
        Integer id,
        String text,
        UserShortDto userDto
) {
}
