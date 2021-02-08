package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.Role;

import java.util.Optional;


public interface RoleService extends ReadWriteService<Role, String> {
    Optional<Role> getRoleByName(String name);
}
