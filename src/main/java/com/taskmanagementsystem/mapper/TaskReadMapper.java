package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.TaskReadDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;

import java.util.List;

public interface TaskReadMapper {

    TaskReadDto map(Task task, List<Comment> comment);
}
