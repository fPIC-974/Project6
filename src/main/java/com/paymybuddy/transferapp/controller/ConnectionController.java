package com.paymybuddy.transferapp.controller;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.IConnectionService;
import com.paymybuddy.transferapp.service.IPaymentService;
import com.paymybuddy.transferapp.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ModelAndView saveConnectionForUser(@RequestParam Integer id,
                                              @RequestParam String email,
                                              Principal principal,
                                              @RequestParam("page") Optional<Integer> page,
                                              @RequestParam("size") Optional<Integer> size) {

        User owner = userService.getUserById(id);
//        User related = null;

        ModelAndView modelAndView = new ModelAndView("transactions");

        /*try {
            related = userService.getUserByEmail(email);
        } catch (NotFoundException notFoundException) {
            modelAndView.addObject("errorMessage", notFoundException.getMessage());
        }*/

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

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

        try {
            User related = userService.getUserByEmail(email);
            connectionService.saveConnection(owner, related);
            modelAndView.addObject("statusMessage", "Connection added");
        } catch (NotFoundException notFoundException) {
            modelAndView.addObject("errorMessage", notFoundException.getMessage());
//            response.addHeader("errorMessage", "Insufficient funds");
        }

        modelAndView.addObject("connections", connectionService.getConnectionsByUserId(id));
        modelAndView.addObject("user", id);
        modelAndView.addObject("payments", payments);

        return modelAndView;
    }
}
