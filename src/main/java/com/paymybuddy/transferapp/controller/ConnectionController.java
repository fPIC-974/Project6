package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.IConnectionService;
import com.paymybuddy.transferapp.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ConnectionController {

    private final IConnectionService connectionService;

    private final IUserService userService;

    public ConnectionController(IConnectionService connectionService,
                                IUserService userService) {
        this.connectionService = connectionService;
        this.userService = userService;
    }

    @PostMapping("connection/addNew")
    public void saveConnectionForUser(@RequestParam Integer id, @RequestParam String email, HttpServletResponse response) throws IOException {
        User owner = userService.getUserById(id);
        User related = userService.getUserByEmail(email);
        connectionService.saveConnection(owner, related);
        response.sendRedirect("/transactions?id=" + id);
    }
}
