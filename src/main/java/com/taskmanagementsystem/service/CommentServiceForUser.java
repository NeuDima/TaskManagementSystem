package com.taskmanagementsystem.service;

import com.taskmanagementsystem.dto.CommentCreateDto;
import com.taskmanagementsystem.dto.CommentReadDto;
import com.taskmanagementsystem.dto.CommentShortDto;

public interface CommentServiceForUser {

    CommentShortDto create(CommentCreateDto commentCreateDto);
}
