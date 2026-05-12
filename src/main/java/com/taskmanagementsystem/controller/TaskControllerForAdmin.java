package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dto.*;
import com.taskmanagementsystem.service.CommentServiceForAdmin;
import com.taskmanagementsystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/admin")
public class TaskControllerForAdmin {

    private final TaskService taskService;
    private final CommentServiceForAdmin commentServiceForAdmin;

    @GetMapping
    public PageResponse<TaskReadDto> getTasks(
            TaskFilter filter,
            @PageableDefault(size = 3)
            Pageable pageable
    ) {
        return taskService.getAllByFilter(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<TaskReadDto> create(@RequestBody TaskCreateEditDto taskCreateEditDto) {
        TaskReadDto taskReadDto = taskService.createTask(taskCreateEditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskReadDto);
    }

    @PatchMapping
    public ResponseEntity<TaskReadDto> updateTask(Integer userId,
                                                  @RequestBody TaskCreateEditDto taskDto) {

        TaskReadDto taskReadDto = taskService.updateTask(userId, taskDto);
        return ResponseEntity.ok(taskReadDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentShortDto> createComment(@RequestBody CommentCreateDto commentCreateDto) {
        CommentShortDto commentReadDto = commentServiceForAdmin.create(commentCreateDto);
        return ResponseEntity.ok(commentReadDto);
    }
}
