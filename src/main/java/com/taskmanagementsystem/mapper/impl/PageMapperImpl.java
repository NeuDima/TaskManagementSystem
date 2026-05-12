package com.taskmanagementsystem.mapper.impl;

import com.taskmanagementsystem.dto.PageResponse;
import com.taskmanagementsystem.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageMapperImpl<T> implements PageMapper<T> {

    @Override
    public PageResponse<T> mapToPageResponse(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
