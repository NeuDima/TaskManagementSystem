package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.CommentReadDto;
import com.taskmanagementsystem.entity.Comment;

public interface CommentReadMapper {

    CommentReadDto map(Comment comment);
}
