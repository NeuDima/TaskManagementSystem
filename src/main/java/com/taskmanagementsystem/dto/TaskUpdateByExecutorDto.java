package com.taskmanagementsystem.dto;

public record TaskUpdateByExecutorDto(
        Integer taskId,
        Integer executorId,
        String newStatus
) {
}
