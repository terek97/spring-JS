package ru.kurbanmagomedov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kurbanmagomedov.models.Role;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

//    Role getRoleById(Long id);
//
//    List<Role> getAllRoles();
//
    Role findByRole(String name);

}
