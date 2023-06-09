package com.paymybuddy.transferapp;

import com.paymybuddy.transferapp.model.Account;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Transfer;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.UserRepository;
import com.paymybuddy.transferapp.service.*;
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
    CommandLineRunner commandLineRunner(IUserService userService,
                                        IAccountService accountService,
                                        IBalanceService balanceService,
                                        ITransferService transferService,
                                        IConnectionService connectionService) {
        return args -> {
            //System.out.println(userService.getUsers());
            /*System.out.println(userService.getUserById(2));
            System.out.println(balanceService.getBalance(2));
            System.out.println("----------");
            Account account = accountService.getAccount(1);
            Balance balance = balanceService.getBalance(2);
            Transfer transfer = new Transfer(500.00, account, balance);
            System.out.println(transferService.saveTransfer(transfer));*/
            //User user = new User("tutu", "TUTU", "tutu@mail.net", "pass");

//            System.out.println(userService.saveUser(user));
            /*User user = userService.saveUser(new User("tutu", "TUTU", "tutu@mail.net", "pass"));

            accountService.saveAccount(new Account("Account1", 111111, user));
            accountService.saveAccount(new Account("Account2", 222222, user));*/

            //User user = userService.getUserById(1);

            //transferService.saveTransfer(new Transfer(50.00, accountService.getAccount(3), balanceService.getBalance(user.getId())));

            //System.out.println(userService.getUserById(user.getId()));

            /*userService.saveUser(new User("titi", "TITI", "titi@mail.net", "pass"));
            userService.saveUser(new User("tata", "TATA", "tata@mail.net", "pass"));*/

            //connectionService.saveConnection(userService.getUserById(1), userService.getUserById(3));

//            System.out.println(userService.getUsers());
            System.out.println(userService.getUserById(1));
        };
    }
}
