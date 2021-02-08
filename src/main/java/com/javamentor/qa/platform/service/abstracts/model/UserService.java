package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long> {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByName(String name);
    void deleteUserByFlag(User userIsDeleted);
}
