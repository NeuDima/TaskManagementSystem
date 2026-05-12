package com.taskmanagementsystem.service.impl;

import com.taskmanagementsystem.dto.CommentCreateDto;
import com.taskmanagementsystem.dto.CommentReadDto;
import com.taskmanagementsystem.dto.CommentShortDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.CommentCreateMapper;
import com.taskmanagementsystem.mapper.CommentReadMapper;
import com.taskmanagementsystem.repository.CommentRepository;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.repository.UserRepository;
import com.taskmanagementsystem.service.CommentService;
import com.taskmanagementsystem.service.CommentServiceForUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceForUserImpl implements CommentServiceForUser {

    private final CommentService commentService;

    @Override
    @Transactional
    public CommentShortDto create(CommentCreateDto commentCreateDto) {
        return commentService.create(commentCreateDto, true);
    }
}
