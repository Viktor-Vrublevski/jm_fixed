package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.*;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.models.util.OnUpdate;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/user/")
@Api(value = "UserApi")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final UserDtoService userDtoService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityHelper securityHelper;
    private static final int MAX_ITEMS_ON_PAGE = 100;

    @Autowired
    public UserController(UserService userService,
                          UserConverter userConverter,
                          UserDtoService userDtoService,
                          PasswordEncoder passwordEncoder,
                          SecurityHelper securityHelper) {

        this.userService = userService;
        this.userConverter = userConverter;
        this.userDtoService = userDtoService;
        this.passwordEncoder = passwordEncoder;
        this.securityHelper = securityHelper;
    }

    // Examples for Swagger
    @GetMapping("{id}")
    @ApiOperation(value = "Return message(Object)", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = UserDto.class),
            @ApiResponse(code = 400, message = "Wrong ID",response = String.class)
    })
    public  ResponseEntity<?> getUserById(
        @ApiParam(name="id",value="type Long(or other descriped)", required = true, example="0")
        @PathVariable Long id){
        Optional<UserDto> userDto = userDtoService.getUserDtoById(id);
        return userDto.isPresent() ? ResponseEntity.ok().body(userDto.get()):
                ResponseEntity.badRequest()
                        .body("User with id " + id + " not found");

   }

    @GetMapping("order/reputation/week")
    @ApiOperation(value = "Get page List<UserDtoList> order by reputation over week." +
            "UserDtoList contains List<TagDto> with size 3 " +
            "and order by activity. Max size entries on page= "+ MAX_ITEMS_ON_PAGE, response = List.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<UserDtoList> order by reputation over week",
                    response = List.class),
            @ApiResponse(code = 400, message = "Wrong value of size or page", response = String.class)
    })
    public ResponseEntity<?> getUserDtoListPaginationByReputationOverWeek(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of records per page.Type int." +
                    "Maximum number of records per page"+ MAX_ITEMS_ON_PAGE , required = true,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<UserDtoList,Object> resultPage = userDtoService.getPageUserDtoListByReputationOverWeek(page, size);

        return  ResponseEntity.ok(resultPage);
    }

    @GetMapping("order/reputation")
    @ApiOperation(value = "Get page List<UserDtoList> order by reputation. " +
            "UserDtoList contains List<TagDto> with size 3 " +
            "and order by activity. Max size entries on page = "+ MAX_ITEMS_ON_PAGE, response = List.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<UserDtoList> order by reputation",
                    response = List.class),
            @ApiResponse(code = 400, message = "Wrong value of size or page", response = String.class)
    })
    public ResponseEntity<?> getUserDtoListPaginationByReputation(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "1")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of records per page. Type int. " +
                    "Maximum number of records per page "+ MAX_ITEMS_ON_PAGE , required = true,
                    example = "10")
            @RequestParam("size") int size) {

        if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }
        PageDto<UserDtoList,Object> resultPage = userDtoService.getPageUserDtoListByReputation(page, size);

        return  ResponseEntity.ok(resultPage);
    }

    // вывод юзеров с репутацией за месяц
    @GetMapping("order/reputation/month")
    @ApiOperation(value = "Get page List<UserDtoList> order by reputation over month." +
            "UserDtoList contains List<TagDto> with size 3 " +
            "and order by activity. Max size entries on page= "+ MAX_ITEMS_ON_PAGE, response = List.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<UserDtoList> order by reputation over month",
                    response = List.class),
            @ApiResponse(code = 400, message = "Wrong ID", response = String.class)
    })
    public ResponseEntity<?> getUserDtoPaginationByReputationOverMonth(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице"+ MAX_ITEMS_ON_PAGE , required = true,
                    example = "10")
            @RequestParam("size") int size) {
        if(page <= 0 || size <=0 || size > MAX_ITEMS_ON_PAGE ) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<UserDtoList, Object> resultPage = userDtoService.getPageUserDtoListByReputationOverMonth(page,size);

        return  ResponseEntity.ok(resultPage);
    }

    @GetMapping("order/reputation/year")
    @ApiOperation(value = "Get page List<UserDtoList> order by reputation over year." +
            "UserDtoList contains List<TagDto> with size 3 " +
            "and order by activity. Max size entries on page= "+ MAX_ITEMS_ON_PAGE, response = List.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the pagination List<UserDtoList> order by reputation over year",
                    response = List.class),
            @ApiResponse(code = 400, message = "Wrong ID", response = String.class)
    })
    public ResponseEntity<?> getUserDtoPaginationByReputationOverYear(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int." +
                    " Максимальное количество записей на странице"+ MAX_ITEMS_ON_PAGE , required = true,
                    example = "10")
            @RequestParam("size") int size) {
        if(page <= 0 || size <=0 || size > MAX_ITEMS_ON_PAGE ) {
            return ResponseEntity.badRequest().body("Номер страницы и размер должны быть " +
                    "положительными. Максимальное количество записей на странице " + MAX_ITEMS_ON_PAGE);
        }

        PageDto<UserDtoList, Object> resultPage = userDtoService.getPageUserDtoListByReputationOverYear(page,size);

        return  ResponseEntity.ok(resultPage);
    }

    @GetMapping("find")
    @ApiOperation(value = "Return page List<UserDtoList>", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Returns the object.", response = List.class),
            @ApiResponse(code = 400, message = "User with this name does not exist", response = String.class)
    })
    public ResponseEntity<?> getUserListByName(
            @ApiParam(name = "page", value = "Number Page. Type int", required = true, example = "10")
            @RequestParam("page") int page,
            @ApiParam(name = "size", value = "Number of entries per page.Type int. " +
                    "Maximum number of records per page " + MAX_ITEMS_ON_PAGE, required = true,
                    example = "10")
            @RequestParam("size") int size,
            @RequestParam("name") String name) {

        if (userService.getUserByName(name).isPresent()) {
            if (page <= 0 || size <= 0 || size > MAX_ITEMS_ON_PAGE) {
                return ResponseEntity.badRequest().body("The page number and size must be positive. " +
                        "Maximum number of records per page " + MAX_ITEMS_ON_PAGE);
            } else {
                PageDto<UserDtoList, Object> resultPage = userDtoService.getPageUserDtoListByName(page, size, name);
                return ResponseEntity.ok(resultPage);
            }
        } else {
            return ResponseEntity.badRequest().body("User with this name does not exist");
        }
    }


    @PostMapping("public/info")
    @ApiOperation(value = "Update user public info", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "User public info updated successfully", response = String.class),
            @ApiResponse(code = 400, message = "Something goes wrong", response = String.class)
    })
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUserDtoPublicInfo(@Valid @RequestBody UserPublicInfoDto userPublicInfoDto) {

        User user = userConverter.userPublicInfoDtoToUser(userPublicInfoDto);
        user.setId(securityHelper.getPrincipal().getId());
        userService.updateUserPublicInfo(user);

        return ResponseEntity.ok(userConverter.userToUserPublicInfoDto(user));
    }


    @PostMapping("password/reset")
    @ApiOperation(value = "Reset user password", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "password reset successfully", response = String.class),
            @ApiResponse(code = 400, message = "Something goes wrong",response = String.class)
    })
    @Validated(OnUpdate.class)
    public ResponseEntity<?> resetPassword (@Valid @RequestBody UserResetPasswordDto userResetPasswordDto) {

        User user = securityHelper.getPrincipal();

        if (!passwordEncoder.matches(userResetPasswordDto.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(userResetPasswordDto.getNewPassword()));
        userService.resetPassword(user);

        return ResponseEntity.ok().body("Password reset successfully");
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "Set up user's field isDeleted to true", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "User deleted successfully", response = String.class),
            @ApiResponse(code = 400, message = "Something goes wrong",response = String.class),
            @ApiResponse(code = 400, message = "The user has already been deleted!",response = String.class)
    })
    public ResponseEntity<?> deleteUser() {

        User user = securityHelper.getPrincipal();
        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            return ResponseEntity.badRequest().body("The user has already been deleted!");
        }
        userService.deleteUserByFlag(user);
        return ResponseEntity.ok().body("User deleted successfully");

    }
}
