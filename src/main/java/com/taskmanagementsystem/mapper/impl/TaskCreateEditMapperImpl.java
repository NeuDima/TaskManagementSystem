package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.TaskCreateEditDto;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.TaskCreateEditMapper;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateEditMapperImpl implements TaskCreateEditMapper {

    @Override
    public Task map(TaskCreateEditDto taskDto, User author, User executor) {
        Task task = new Task();
        return copy(task, taskDto, author, executor);
    }

    @Override
    public Task map(Task task, TaskCreateEditDto taskDto, User author, User executor) {
        return copy(task, taskDto, author, executor);
    }

    @NonNull
    private Task copy(Task task, TaskCreateEditDto taskDto, User author, User executor) {
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setPriority(taskDto.priority());
        task.setStatus(taskDto.status());
        task.setAuthor(author);
        task.setExecutor(executor);
        return task;
    }
}
