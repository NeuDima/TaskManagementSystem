package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.TaskCreateEditDto;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;

public interface TaskCreateEditMapper {

    Task map(TaskCreateEditDto taskCreateEditDto, User author, User executor);

    Task map(Task task, TaskCreateEditDto taskCreateEditDto, User author, User executor);
}
