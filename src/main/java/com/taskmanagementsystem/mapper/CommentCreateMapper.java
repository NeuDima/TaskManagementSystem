package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.CommentCreateDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;

public interface CommentCreateMapper {

    Comment map(CommentCreateDto commentDto, User user, Task task);
}
