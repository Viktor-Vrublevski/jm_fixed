package com.javamentor.qa.platform.service.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PaginationServiceImpl<T, V> implements PaginationService<T,V> {

    @Autowired
    private  Map<String, PaginationDao<T>> pageBean;

    @Override
    public PageDto<T, V> getPageDto(String methodName, Map<String, Object> parameters) {
        PageDto<T, V> pageDto = new PageDto<>();
        PaginationDao<T> paginationDaoBean = pageBean.get(methodName);

        int totalResultCount = paginationDaoBean.getCount(parameters);

        pageDto.setCurrentPageNumber((int)parameters.get("page"));
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setItemsOnPage((int)parameters.get("size"));
        pageDto.setItems(paginationDaoBean.getItems(parameters));
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double)((int)parameters.get("size"))));

        return pageDto;
    }

}
