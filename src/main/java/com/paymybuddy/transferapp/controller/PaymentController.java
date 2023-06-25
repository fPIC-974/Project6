package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.IBalanceService;
import com.paymybuddy.transferapp.service.IPaymentService;
import com.paymybuddy.transferapp.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PaymentController {

    private final IUserService userService;

    private final IPaymentService paymentService;

    private final IBalanceService balanceService;

    public PaymentController(IUserService userService,
                             IPaymentService paymentService,
                             IBalanceService balanceService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.balanceService = balanceService;
    }

    @PostMapping("payment/addNew")
    public void savePaymentForUser(@RequestParam Integer user,
                                   @RequestParam Integer id,
                                   @RequestParam Integer amount,
                                   @RequestParam String description,
                                   HttpServletResponse response) throws IOException {

        Balance balance = balanceService.getBalance(user);

        User recipient = userService.getUserById(id);

        paymentService.savePayment(new Payment(amount, description, balance, recipient));

        response.sendRedirect("/transactions?id=" + user);
    }
}
