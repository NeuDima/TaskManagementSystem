package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.TaskReadDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.mapper.CommentShortMapper;
import com.taskmanagementsystem.mapper.TaskReadMapper;
import com.taskmanagementsystem.mapper.UserShortMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskReadMapperImpl implements TaskReadMapper {

    private final UserShortMapper userShortMapper;
    private final CommentShortMapper commentShortMapper;

    @Override
    public TaskReadDto map(Task task, List<Comment> comment) {
        return new TaskReadDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),

                userShortMapper.map(task.getAuthor()),
                userShortMapper.map(task.getExecutor()),

                comment.stream()
                        .map(commentShortMapper::map)
                        .toList()
        );
    }
}
