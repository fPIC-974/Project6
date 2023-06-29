package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.IBalanceService;
import com.paymybuddy.transferapp.service.IConnectionService;
import com.paymybuddy.transferapp.service.IPaymentService;
import com.paymybuddy.transferapp.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class PaymentController {

    private final IUserService userService;

    private final IPaymentService paymentService;

    private final IBalanceService balanceService;

    private final IConnectionService connectionService;

    public PaymentController(IUserService userService,
                             IPaymentService paymentService,
                             IBalanceService balanceService,
                             IConnectionService connectionService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.balanceService = balanceService;
        this.connectionService = connectionService;
    }

    @PostMapping("payment/addNew")
    public ModelAndView savePaymentForUser(@RequestParam Integer user,
                                   @RequestParam Integer id,
                                   @RequestParam Integer amount,
                                   @RequestParam String description,
                                   HttpServletResponse response) throws IOException {

        Balance balance = balanceService.getBalance(user);

        User recipient = userService.getUserById(id);

        ModelAndView modelAndView = new ModelAndView("transactions");

        try {
            paymentService.savePayment(new Payment(amount, description, balance, recipient));
        } catch (RuntimeException runtimeException) {
            modelAndView.addObject("errorMessage", "Insufficient Funds");
            response.addHeader("errorMessage", "Insufficient funds");
        }

        List<Payment> payments = paymentService.getPaymentsByBalanceId(user);

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(user));
        modelAndView.addObject("user", user);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }
}
