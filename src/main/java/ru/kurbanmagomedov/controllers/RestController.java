package ru.kurbanmagomedov.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kurbanmagomedov.models.Role;
import ru.kurbanmagomedov.models.User;
import ru.kurbanmagomedov.service.RoleService;
import ru.kurbanmagomedov.service.UserService;

import java.security.Principal;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userService;
    private final RoleService roleService;

    public RestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @PatchMapping("/change")
    public User changeUser(@RequestBody User user) {
        userService.setUser(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public void removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
    }

    @PostMapping("/new")
    public User createUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }
}
