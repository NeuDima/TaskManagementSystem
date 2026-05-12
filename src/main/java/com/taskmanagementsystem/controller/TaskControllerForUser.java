package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dto.*;
import com.taskmanagementsystem.service.CommentServiceForUser;
import com.taskmanagementsystem.service.TaskServiceForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/user")
public class TaskControllerForUser {

    private final TaskServiceForUser taskServiceForUser;
    private final CommentServiceForUser commentServiceForUser;


    @GetMapping
    public PageResponse<TaskReadDto> getTasks(
            @PageableDefault(size = 3) Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        return taskServiceForUser.getAllByFilter(email, pageable);
    }

    @PatchMapping
    public ResponseEntity<TaskReadDto> updateTask(@RequestBody TaskUpdateByExecutorDto taskDto) {

        TaskReadDto taskReadDto = taskServiceForUser.updateTask(taskDto);
        return ResponseEntity.ok(taskReadDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(Integer userId,
                                           Integer taskId) {
        taskServiceForUser.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CommentShortDto> createComment(@RequestBody CommentCreateDto commentCreateDto) {
        CommentShortDto commentReadDto = commentServiceForUser.create(commentCreateDto);
        return ResponseEntity.ok(commentReadDto);
    }
}
