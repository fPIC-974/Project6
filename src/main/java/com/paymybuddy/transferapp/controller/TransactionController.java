package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.*;
import org.dom4j.rule.Mode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TransactionController {

    private final IUserService userService;

    private final ITransferService transferService;

    private final IConnectionService connectionService;

    private final IPaymentService paymentService;

    public TransactionController(
            IUserService userService,
            ITransferService transferService,
            IConnectionService connectionService,
            IPaymentService paymentService) {
        this.userService = userService;
        this.transferService = transferService;
        this.connectionService = connectionService;
        this.paymentService = paymentService;
    }

    @GetMapping("/transactions")
    public ModelAndView getTransactionsByUserId(@RequestParam Integer id) {

        ModelAndView modelAndView = new ModelAndView("transactions");

        /*User user = userService.getUserById(id);
        List<User> connections = user.getConnections();*/
        List<Payment> payments = paymentService.getPaymentsByBalanceId(id);

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(id));
        modelAndView.addObject("user", id);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }
}
