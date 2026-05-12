package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.CommentShortDto;
import com.taskmanagementsystem.entity.Comment;

public interface CommentShortMapper {

    CommentShortDto map(Comment comment);
}
