package com.taskmanagementsystem.service.impl;

import com.taskmanagementsystem.dto.CommentCreateDto;
import com.taskmanagementsystem.dto.CommentReadDto;
import com.taskmanagementsystem.dto.CommentShortDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.CommentCreateMapper;
import com.taskmanagementsystem.mapper.CommentReadMapper;
import com.taskmanagementsystem.mapper.CommentShortMapper;
import com.taskmanagementsystem.repository.CommentRepository;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.repository.UserRepository;
import com.taskmanagementsystem.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentReadMapper commentReadMapper;
    private final CommentCreateMapper commentCreateMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentShortMapper commentShortMapper;

    @Override
    public CommentShortDto getById(Integer id) {
        return commentRepository.findById(id)
                .map(commentShortMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));
    }

    @Override
    @Transactional
    public CommentShortDto create(CommentCreateDto commentCreateDto, boolean checkExecutor) {
        if (commentCreateDto == null) {
            throw new IllegalArgumentException("CommentCreateDto is null");
        }

        User user = userRepository.findById(commentCreateDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + commentCreateDto.userId()));

        Task task = taskRepository.findById(commentCreateDto.taskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + commentCreateDto.taskId()));

        if (checkExecutor && !user.equals(task.getExecutor())) {
            throw new IllegalArgumentException("The user is not the executor of the task");
        }

        Comment comment = commentCreateMapper.map(commentCreateDto, user, task);
        Comment savedComment = commentRepository.save(comment);

        return commentShortMapper.map(savedComment);
    }
}
