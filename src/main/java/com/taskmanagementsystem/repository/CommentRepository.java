package com.taskmanagementsystem.repository;

import com.taskmanagementsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByTaskId(Integer id);
}
