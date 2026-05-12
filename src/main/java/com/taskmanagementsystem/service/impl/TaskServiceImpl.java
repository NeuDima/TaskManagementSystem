package com.taskmanagementsystem.service.impl;

import com.taskmanagementsystem.dto.*;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.PageMapper;
import com.taskmanagementsystem.mapper.TaskCreateEditMapper;
import com.taskmanagementsystem.mapper.TaskReadMapper;
import com.taskmanagementsystem.mapper.TaskUpdateByExecutorMapper;
import com.taskmanagementsystem.repository.CommentRepository;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.repository.UserRepository;
import com.taskmanagementsystem.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskReadMapper taskReadMapper;
    private final TaskCreateEditMapper taskCreateEditMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PageMapper<TaskReadDto> pageMapper;

    @Override
    public TaskReadDto getById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        List<Comment> comments = commentRepository.findAllByTaskId(id);

        return taskReadMapper.map(task, comments);
    }

    @Override
    public PageResponse<TaskReadDto> getAllByFilter(TaskFilter filter, Pageable pageable) {
        Page<TaskReadDto> pages = taskRepository.findAll(taskRepository.findAllByFilter(filter), pageable)
                .map(task -> taskReadMapper.map(
                        task,
                        commentRepository.findAllByTaskId(task.getId())));

        return pageMapper.mapToPageResponse(pages);
    }

    @Override
    @Transactional
    public TaskReadDto createTask(TaskCreateEditDto taskDto) {
        if (taskDto == null) {
            throw new IllegalStateException("Failed to create task");
        }

        User author = userRepository.findById(taskDto.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + taskDto.authorId()));
        User executor = userRepository.findById(taskDto.executorId())
                .orElseThrow(() -> new EntityNotFoundException("Executor not found with id: " + taskDto.executorId()));

        Task task = taskCreateEditMapper.map(taskDto, author, executor);
        task = taskRepository.save(task);
        List<Comment> comments = List.of();
        return taskReadMapper.map(task, comments);
    }

    @Override
    @Transactional
    public void deleteTask(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Task not found with id: " + id
                ));

        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskReadDto updateTask(Integer id, TaskCreateEditDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException("taskDto is null");
        }

        User author = userRepository.findById(taskDto.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + taskDto.authorId()));
        User executor = userRepository.findById(taskDto.executorId())
                .orElseThrow(() -> new EntityNotFoundException("Executor not found with id: " + taskDto.executorId()));

        return taskRepository.findById(id)
                .map(task -> taskCreateEditMapper.map(task, taskDto, author, executor))
                .map(taskRepository::save)
                .map(task -> taskReadMapper.map(
                        task,
                        commentRepository.findAllByTaskId(task.getId())))
                .orElseThrow(() -> new IllegalArgumentException("Id is null"));
    }
}
