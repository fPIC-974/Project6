package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.*;
import org.dom4j.rule.Mode;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public ModelAndView getTransactionsByUserId(Principal principal,
                                                @RequestParam("page") Optional<Integer> page,
                                                @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Integer id = userService.getUserByEmail(principal.getName()).getId();

        ModelAndView modelAndView = new ModelAndView("transactions");

        List<Payment> payments = paymentService.getPaymentsByBalanceId(id);

        Page<Payment> paymentPage = paymentService.getPaymentsPaginated(PageRequest.of(currentPage - 1, pageSize), id);

        modelAndView.addObject("paymentPage", paymentPage);
        modelAndView.addObject("currentPage", currentPage);

        int totalPages = paymentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelAndView.addObject("pageNumbers", pageNumbers);
        }



        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(id));
        modelAndView.addObject("user", id);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }

    /*public ModelAndView defaultView(
            Principal principal,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        // Get current user id
        Integer id = userService.getUserByEmail(principal.getName()).getId();

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        ModelAndView modelAndView = new ModelAndView("transactions");

        List<Payment> payments = paymentService.getPaymentsByBalanceId(id);

        Page<Payment> paymentPage = paymentService.getPaymentsPaginated(PageRequest.of(currentPage - 1, pageSize), id);

        modelAndView.addObject("paymentPage", paymentPage);
        modelAndView.addObject("currentPage", currentPage);

        int totalPages = paymentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(id));
        modelAndView.addObject("user", id);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }*/
}
