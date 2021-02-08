package com.javamentor.qa.platform.service.impl.dto.pagination.question;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationDao;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class PaginationQuestionDtoService implements PaginationService<QuestionDto, Object> {

    @Autowired
    private  Map<String, PaginationDao<QuestionDto>> pageBean;

    @Autowired
    private QuestionDao questionDao;

    @Override
    public PageDto<QuestionDto, Object> getPageDto(String methodName, Map<String, Object> parameters) {

        int page = (int)parameters.get("page");
        int size = (int)parameters.get("size");

        List<Long> questionIds = questionDao.getPaginationQuestionIds(page, size);
        parameters.put("questionIds", questionIds);

        PageDto<QuestionDto, Object> pageDto = new PageDto<>();
        PaginationDao<QuestionDto> paginationDaoBean = pageBean.get(methodName);

        int totalResultCount = paginationDaoBean.getCount(parameters);

        pageDto.setCurrentPageNumber(page);
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setItemsOnPage(size);
        pageDto.setItems(paginationDaoBean.getItems(parameters));
        pageDto.setTotalPageCount((int) Math.ceil(totalResultCount / (double) size));

        return pageDto;
    }
}
