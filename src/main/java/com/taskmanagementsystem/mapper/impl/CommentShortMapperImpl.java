package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.CommentShortDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.mapper.CommentShortMapper;
import com.taskmanagementsystem.mapper.UserShortMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentShortMapperImpl implements CommentShortMapper {

    private final UserShortMapper userShortMapper;

    @Override
    public CommentShortDto map(Comment comment) {
        return new CommentShortDto(
                comment.getId(),
                comment.getText(),
                userShortMapper.map(comment.getUser())
        );
    }
}
