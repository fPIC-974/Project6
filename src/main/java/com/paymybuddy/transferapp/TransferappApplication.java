package com.paymybuddy.transferapp;

import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.UserRepository;
import com.paymybuddy.transferapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TransferappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferappApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            System.out.println(userService.getUsers());
            userService.deleteUser(1);
            System.out.println(userService.getUsers());
        };
    }
}
