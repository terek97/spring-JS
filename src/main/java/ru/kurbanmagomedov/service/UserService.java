package ru.kurbanmagomedov.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kurbanmagomedov.models.Role;
import ru.kurbanmagomedov.models.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {


    void saveUser(User user);

    User getUserById(Long id);

    void setUser(User user);

    void removeUser(Long id);

    List<User> getAllUsers();

    void addRole(User user, Role role);

    User getUserByUsername(String username);
}
