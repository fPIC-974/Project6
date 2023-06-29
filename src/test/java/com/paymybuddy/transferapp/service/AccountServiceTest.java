package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Account;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        User user = new User("John", "Doe", "jdoe@mail.net", "pass");
        Account account = new Account("jdoeAccount", 111111, user);
    }

    @Test
    public void saveExistingAccount() {

    }

    @Test
    public void saveNonExistingAccount() {

    }

    @Test
    public void updateNonExistingAccount() {

    }

    @Test
    public void updateExistingAccount() {

    }
}