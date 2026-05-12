package com.taskmanagementsystem.mapper;

import com.taskmanagementsystem.dto.PageResponse;
import org.springframework.data.domain.Page;

public interface PageMapper<T> {

    PageResponse<T> mapToPageResponse(Page<T> page);
}
