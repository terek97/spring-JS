package ru.kurbanmagomedov.service;

import ru.kurbanmagomedov.models.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);

    Role getRoleByName(String name);

    List<Role> getAllRoles();
}
