package com.taskmanagementsystem.dto;

public record TaskFilter(
        Integer authorId,
        Integer executorId
) {
}
