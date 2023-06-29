package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.IConnectionService;
import com.paymybuddy.transferapp.service.IPaymentService;
import com.paymybuddy.transferapp.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class ConnectionController {

    private final IConnectionService connectionService;

    private final IUserService userService;

    private final IPaymentService paymentService;

    public ConnectionController(IConnectionService connectionService,
                                IUserService userService,
                                IPaymentService paymentService) {
        this.connectionService = connectionService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping("connection/addNew")
    public ModelAndView saveConnectionForUser(@RequestParam Integer id, @RequestParam String email, HttpServletResponse response) throws IOException {
        User owner = userService.getUserById(id);
        User related = userService.getUserByEmail(email);

        ModelAndView modelAndView = new ModelAndView("transactions");
        try {
            connectionService.saveConnection(owner, related);
        } catch (RuntimeException runtimeException) {
            modelAndView.addObject("errorMessage", runtimeException.getMessage());
            response.addHeader("errorMessage", "Insufficient funds");
        }

        List<Payment> payments = paymentService.getPaymentsByBalanceId(id);

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(id));
        modelAndView.addObject("user", id);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }
}
