package com.taskmanagementsystem.service.impl;

import com.taskmanagementsystem.dto.PageResponse;
import com.taskmanagementsystem.dto.TaskFilter;
import com.taskmanagementsystem.dto.TaskReadDto;
import com.taskmanagementsystem.dto.TaskUpdateByExecutorDto;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.TaskReadMapper;
import com.taskmanagementsystem.mapper.TaskUpdateByExecutorMapper;
import com.taskmanagementsystem.repository.CommentRepository;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.repository.UserRepository;
import com.taskmanagementsystem.service.TaskService;
import com.taskmanagementsystem.service.TaskServiceForUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceForUserImpl implements TaskServiceForUser {

    private final TaskRepository taskRepository;
    private final TaskUpdateByExecutorMapper taskUpdateByExecutorMapper;
    private final TaskReadMapper taskReadMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

    @Override
    public PageResponse<TaskReadDto> getAllByFilter(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        TaskFilter filter = new TaskFilter(null, user.getId());

        return taskService.getAllByFilter(filter, pageable);
    }

    @Override
    @Transactional
    public void deleteTask(Integer userId, Integer taskId) {
        if (userId == null || taskId == null) {
            throw new IllegalArgumentException("Id is null");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Task not found with id: " + taskId
                ));

        if (!userId.equals(task.getAuthor().getId())) {
            throw new IllegalStateException("The user is not the author of the task");
        }

        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskReadDto updateTask(TaskUpdateByExecutorDto updateTaskDto) {
        return taskRepository.findById(updateTaskDto.taskId())
                .map(task -> taskUpdateByExecutorMapper.map(task, updateTaskDto))
                .map(taskRepository::save)
                .map(task -> taskReadMapper.map(
                        task,
                        commentRepository.findAllByTaskId(task.getId())))
                .orElseThrow(() -> new IllegalArgumentException("Id is null"));
    }
}
