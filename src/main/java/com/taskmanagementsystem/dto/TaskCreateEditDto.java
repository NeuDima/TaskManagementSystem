package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.entity.util.Priority;
import com.taskmanagementsystem.entity.util.Status;

public record TaskCreateEditDto(

        String title,
        String description,
        Status status,
        Priority priority,
        Integer authorId,
        Integer executorId
) {
}
