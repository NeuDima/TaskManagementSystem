package com.taskmanagementsystem.service;

import com.taskmanagementsystem.dto.*;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskReadDto getById(Integer id);

    PageResponse<TaskReadDto> getAllByFilter(TaskFilter filter, Pageable pageable);

    TaskReadDto createTask(TaskCreateEditDto taskCreateEditDto);

    void deleteTask(Integer taskId);

    TaskReadDto updateTask(Integer id, TaskCreateEditDto updateTaskDto);
}
