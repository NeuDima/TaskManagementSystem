package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.TaskUpdateByExecutorDto;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.util.Status;
import com.taskmanagementsystem.mapper.TaskUpdateByExecutorMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdateByExecutorMapperImpl implements TaskUpdateByExecutorMapper {

    @Override
    public Task map(Task task, TaskUpdateByExecutorDto updateTaskDto) {
        task.setStatus(Status.valueOf(updateTaskDto.newStatus()));
        return task;
    }
}
