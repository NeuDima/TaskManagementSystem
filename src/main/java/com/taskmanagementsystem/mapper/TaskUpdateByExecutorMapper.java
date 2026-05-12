package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.TaskUpdateByExecutorDto;
import com.taskmanagementsystem.entity.Task;

public interface TaskUpdateByExecutorMapper {

    Task map(Task task, TaskUpdateByExecutorDto updateTaskDto);
}
