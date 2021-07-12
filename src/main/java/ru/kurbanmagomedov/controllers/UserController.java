package ru.kurbanmagomedov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getUser() {
        return "user";
    }
}
