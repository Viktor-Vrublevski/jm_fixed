package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final UserDtoDao userDtoDao;
    private final PaginationService<UserDtoList, Object> paginationService;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao, PaginationService<UserDtoList, Object> paginationService) {
        this.userDtoDao = userDtoDao;
        this.paginationService = paginationService;
    }

    @Override
    public Optional<UserDto> getUserDtoById(long id) {
        return userDtoDao.getUserById(id);
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverYear(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(365)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverWeek(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(7)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputation(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputation",
                setPaginationParameters(page, size, Optional.empty(), Optional.empty()));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverMonth(int page, int size) {
        return paginationService.getPageDto(
                "paginationUserByReputationOverPeriod",
                setPaginationParameters(page, size, Optional.empty(), Optional.of(30)));
    }

    @Override
    public PageDto<UserDtoList, Object> getPageUserDtoListByName(int page, int size, String name) {
        return paginationService.getPageDto(
                "paginationUserReputationByName",
                setPaginationParameters(page, size, Optional.ofNullable(name), Optional.empty()));
    }

    private Map<String, Object> setPaginationParameters(int page, int size, Optional<String> name, Optional<Integer> days) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("page", page);
        parameters.put("size", size);
        parameters.put("name", name.orElse(""));
        parameters.put("days", days.orElse(30));
        return parameters;
    }

}
