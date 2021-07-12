package ru.kurbanmagomedov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }
}
