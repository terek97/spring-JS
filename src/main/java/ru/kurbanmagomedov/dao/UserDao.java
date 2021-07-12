package ru.kurbanmagomedov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kurbanmagomedov.models.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
