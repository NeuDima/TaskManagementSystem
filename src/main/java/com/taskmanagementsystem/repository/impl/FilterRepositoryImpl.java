package com.taskmanagementsystem.repository.impl;

import com.taskmanagementsystem.dto.TaskFilter;
import com.taskmanagementsystem.entity.Task;
import com.taskmanagementsystem.repository.FilterRepository;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterRepositoryImpl implements FilterRepository<TaskFilter, Task> {

    @Override
    public Specification<Task> findAllByFilter(TaskFilter filter) {
        return (task, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.authorId() != null && filter.authorId() >= 0) {
                predicates.add(
                        cb.equal(
                                task.get("author").get("id"),
                                filter.authorId()));
            }
            if (filter.executorId() != null && filter.executorId() >= 0) {
                predicates.add(
                        cb.equal(
                                task.get("executor").get("id"),
                                filter.executorId()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
