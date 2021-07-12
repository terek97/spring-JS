package ru.kurbanmagomedov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kurbanmagomedov.dao.RoleDao;
import ru.kurbanmagomedov.models.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleById(Long id) {
//        return roleDao.getRoleById(id);
        return roleDao.getById(id);
    }

    @Override
    public Role getRoleByName(String name) {
//        return roleDao.getRoleByName(name);
        return roleDao.findByRole(name);
    }

    @Override
    public List<Role> getAllRoles() {
//        return roleDao.getAllRoles();
        return roleDao.findAll();
    }
}
