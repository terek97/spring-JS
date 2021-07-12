package ru.kurbanmagomedov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kurbanmagomedov.dao.UserDao;
import ru.kurbanmagomedov.models.Role;
import ru.kurbanmagomedov.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements ru.kurbanmagomedov.service.UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void saveUser(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

//        userDao.saveUser(user);
        userDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
//        return userDao.getUserById(id);
        return userDao.getById(id);
    }

    @Override
    public void setUser(User user) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Optional<User> userInDB = userDao.findById(user.getId());
        if (!userInDB.map(User::getPassword).get().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }


//        userDao.setUser(user);

//        внутри этого метода вызывается merge, если сущность не является новой
        userDao.save(user);
    }

    @Override
    public void removeUser(Long id) {
//        userDao.removeUser(id);
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
        return userDao.findAll();
    }

    @Override
    public void addRole(User user, Role role) {
        Set<Role> roleSet;

        if (user.getRoles() != null) {
            roleSet = user.getRoles();
        } else {
            roleSet = new HashSet<>();
        }
        roleSet.add(role);
        user.setRoles(roleSet);
    }

    @Override
    public User getUserByUsername(String username) {
//        return userDao.loadUserByUsername(username);
        return userDao.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
