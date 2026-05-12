package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.CommentReadDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.mapper.CommentReadMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentReadMapperImpl implements CommentReadMapper {

    @Override
    public CommentReadDto map(Comment comment) {
        return new CommentReadDto(
                comment.getId(),
                comment.getUser(),
                comment.getTask(),
                comment.getText()
        );
    }
}
