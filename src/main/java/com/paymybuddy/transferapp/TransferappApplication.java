package com.paymybuddy.transferapp;

import com.paymybuddy.transferapp.model.Account;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Transfer;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.service.*;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

//@SpringBootApplication ( exclude = {SecurityAutoConfiguration.class} )
@SpringBootApplication
public class TransferappApplication implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(TransferappApplication.class);

    private final UserService userService;
    private final BalanceService balanceService;
    private final ConnectionService connectionService;
    private final PaymentService paymentService;

    public TransferappApplication(UserService userService,
                                BalanceService balanceService,
                                ConnectionService connectionService,
                                PaymentService paymentService) {
        this.userService = userService;
        this.balanceService = balanceService;
        this.connectionService = connectionService;
        this.paymentService = paymentService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransferappApplication.class, args);
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.warn("Starting application ...");
        //System.out.println(userService.getUsers());
        /*User user = userService.getUserById(1);
        Balance balance = balanceService.getBalance(user.getId());
        balance.getTransfers().forEach(transfer -> {
            System.out.println("From Account : " + transfer.getAccount().getName()
            + " Amount : " + transfer.getAmount()
            + " Date : " + transfer.getDate());
        });

        balance.getPayments().forEach(payment -> {
            System.out.println("From " + payment.getUser()
                    + " Amount : " + payment.getAmount()
                    + " Date : " + payment.getDate());
        });*/

        /*User userById4 = userService.getUserById(4);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userById4.setPassword(passwordEncoder.encode("password"));
        userService.updateUser(4, userById4);*/
    }
}
