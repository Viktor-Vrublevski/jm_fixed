package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserDtoList;

import java.util.Optional;

public interface UserDtoService {

    Optional <UserDto> getUserDtoById(long id);

    PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverYear(int page, int size);

    PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverMonth(int page, int size);

    PageDto<UserDtoList, Object> getPageUserDtoListByReputationOverWeek(int page, int size);

    PageDto<UserDtoList, Object> getPageUserDtoListByReputation(int page, int size);

    PageDto<UserDtoList,Object> getPageUserDtoListByName(int page, int size, String name);

}
