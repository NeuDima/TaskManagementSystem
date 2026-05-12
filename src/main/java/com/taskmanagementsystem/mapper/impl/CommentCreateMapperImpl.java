package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.CommentCreateDto;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.mapper.CommentCreateMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentCreateMapperImpl implements CommentCreateMapper {

    @Override
    public Comment map(CommentCreateDto commentDto, User user, Task task) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTask(task);
        comment.setText(commentDto.text());
        return comment;
    }
}
