package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.getUsers());
        return modelAndView;
    }

/*    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users= userService.getUsers();
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id).orElse(null);
    }*/
}
