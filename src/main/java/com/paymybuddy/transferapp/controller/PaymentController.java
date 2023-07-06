package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.IBalanceService;
import com.paymybuddy.transferapp.service.IConnectionService;
import com.paymybuddy.transferapp.service.IPaymentService;
import com.paymybuddy.transferapp.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    @PostMapping("payments/addNew")
    public ModelAndView savePaymentForUser(@RequestParam Integer user,
                                           @RequestParam Integer id,
                                           @RequestParam Integer amount,
                                           @RequestParam String description,
                                           Principal principal,
                                           @RequestParam("page") Optional<Integer> page,
                                           @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Balance balance = balanceService.getBalance(user);

        User recipient = userService.getUserById(id);

        ModelAndView modelAndView = new ModelAndView("transactions");

        List<Payment> payments = paymentService.getPaymentsByBalanceId(user);

        Page<Payment> paymentPage = paymentService.getPaymentsPaginated(PageRequest.of(currentPage - 1, pageSize), user);

        modelAndView.addObject("paymentPage", paymentPage);
        modelAndView.addObject("currentPage", currentPage);

        int totalPages = paymentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(user));
        modelAndView.addObject("user", user);
        modelAndView.addObject("payments", payments);

        try {
            paymentService.savePayment(new Payment(amount, description, balance, recipient));
            modelAndView.addObject("statusMessage", "Payment done");
        } catch (RuntimeException runtimeException) {
            modelAndView.addObject("errorMessage", runtimeException.getMessage());
//            response.addHeader("errorMessage", "Insufficient funds");
        }

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(user));
        modelAndView.addObject("user", user);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }
}
