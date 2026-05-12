package com.taskmanagementsystem.service.impl;

import com.taskmanagementsystem.dto.CommentCreateDto;
import com.taskmanagementsystem.dto.CommentReadDto;
import com.taskmanagementsystem.dto.CommentShortDto;
import com.taskmanagementsystem.service.CommentService;
import com.taskmanagementsystem.service.CommentServiceForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceForAdminImpl implements CommentServiceForAdmin {

    private final CommentService commentService;

    @Override
    @Transactional
    public CommentShortDto create(CommentCreateDto commentCreateDto) {
        return commentService.create(commentCreateDto, false);
    }
}
