package com.javamentor.qa.platform.service.abstracts.dto.pagination;

import com.javamentor.qa.platform.models.dto.PageDto;

import java.util.Map;

public interface PaginationService<T, V>{
    PageDto<T, V> getPageDto(String methodName, Map<String, Object> parameters);
}
