package com.taskmanagementsystem.dto;

public record CommentCreateDto(
        Integer userId,
        Integer taskId,
        String text
) {
}
