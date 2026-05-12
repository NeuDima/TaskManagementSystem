package com.taskmanagementsystem.service;

import com.taskmanagementsystem.dto.PageResponse;
import com.taskmanagementsystem.dto.TaskReadDto;
import com.taskmanagementsystem.dto.TaskUpdateByExecutorDto;
import org.springframework.data.domain.Pageable;

public interface TaskServiceForUser {

    PageResponse<TaskReadDto> getAllByFilter(String email, Pageable pageable);

    void deleteTask(Integer userId, Integer taskId);

    TaskReadDto updateTask(TaskUpdateByExecutorDto updateTaskDto);
}
