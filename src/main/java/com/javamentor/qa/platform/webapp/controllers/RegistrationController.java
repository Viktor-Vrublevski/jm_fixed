package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.util.OnCreate;
import com.javamentor.qa.platform.security.jwt.JwtUtils;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.configs.mail.MailService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("api/auth/reg/")
@Api(value = "RegApi")
public class RegistrationController {
    @Value("${address.mail.confirm}")
    private String address;
    private final UserService userService;
    private final UserConverter userConverter;
    private final JwtUtils jwtUtils;
    private final MailService mailService;
    private final String subject = "Registration confirm";
    private final String text = "For finish registration follow to link ";

    @Autowired
    public RegistrationController(UserService userService,
                                  UserConverter userConverter,
                                  JwtUtils jwtUtils,
                                  MailService mailService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.jwtUtils = jwtUtils;
        this.mailService = mailService;
    }

    @PostMapping("registration")
    @Validated(OnCreate.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto ) {

        if (!userService.getUserByEmail(userRegistrationDto.getEmail()).isPresent()) {
            User user = userConverter.userDtoToUser(userRegistrationDto);
            user.setIsEnabled(false);
            userService.persist(user);
            String token = jwtUtils.generateRegJwtToken(userRegistrationDto.getEmail());
            mailService.sendSimpleMessage(user.getEmail(), subject, text  + address + "registration/confirm?token=" + token);
            return ResponseEntity.ok(userConverter.userToDto(user));
        }
        else {
            System.out.println(userService.getUserByEmail(userRegistrationDto.getEmail()).get().isEnabled());
            if (!userService.getUserByEmail(userRegistrationDto.getEmail()).get().isEnabled()) {
                return ResponseEntity.badRequest().body("Registration is not completed");
            }
            return ResponseEntity.badRequest().body("User with email " + userRegistrationDto.getEmail() +
                    " already exist");
        }
    }

    @GetMapping("confirm")
    public ResponseEntity<?> confirmUser(@ApiParam String token){
        try {
            User user = userService.getUserByEmail(jwtUtils.getUsernameFromToken(token)).get();
            user.setIsEnabled(true);
            userService.update(user);
            return ResponseEntity.ok().body(userConverter.userToDto(user));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}