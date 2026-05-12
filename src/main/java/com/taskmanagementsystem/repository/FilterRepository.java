package com.taskmanagementsystem.repository;

import org.springframework.data.jpa.domain.Specification;

public interface FilterRepository<F, E> {

    Specification<E> findAllByFilter(F filter);
}
