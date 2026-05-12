package com.taskmanagementsystem.repository;

import com.taskmanagementsystem.dto.TaskFilter;
import com.taskmanagementsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface TaskRepository extends
        JpaRepository<Task, Integer>,
        JpaSpecificationExecutor<Task>,
        FilterRepository<TaskFilter, Task> {
}
