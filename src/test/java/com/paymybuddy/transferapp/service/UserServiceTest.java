package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.AlreadyExistsException;
import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        User user = new User("John", "Doe", "jdoe@mail.net", "pass");
        Balance balance = new Balance(user);
        user.setBalance(balance);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(balanceRepository.save(balance)).thenReturn(balance);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

    }

    @Test
    public void saveExistingUser() {


        User toSave = new User("John", "Doe", "jdoe@mail.net", "pass");

        when(userRepository.existsByEmail("jdoe@mail.net")).thenReturn(true);


        AlreadyExistsException aee = assertThrows(AlreadyExistsException.class,
                () -> userService.saveUser(toSave));

        assertEquals("User already exists", aee.getMessage());

        reset(balanceRepository);
        reset(userRepository);
    }

    @Test
    public void saveNonExistingUser() {

        User toSave = new User("John", "Doe", "jdoe@mail.net", "pass");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        User toCheck = userService.saveUser(toSave);

        assertNotNull(toCheck);
        assertEquals("John", toCheck.getFirstName());
        assertEquals("Doe", toCheck.getLastName());
        assertEquals("jdoe@mail.net", toCheck.getEmail());
        assertEquals("pass", toCheck.getPassword());

        reset(balanceRepository);
        reset(userRepository);
    }

    @Test
    public void updateExistingUser() {
        reset(balanceRepository);

        User toSave = new User("James", "Burton", "jburton@mail.net", "password");

        User toCheck = userService.updateUser(1, toSave);

        assertNotNull(toCheck);
        assertEquals("James", toCheck.getFirstName());
        assertEquals("Burton", toCheck.getLastName());
        assertEquals("jburton@mail.net", toCheck.getEmail());
        assertEquals("password", toCheck.getPassword());
    }

    @Test
    public void updateNonExistingUser() {
        reset(balanceRepository);
        reset(userRepository);

        User toSave = new User("James", "Burton", "jburton@mail.net", "password");

        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException nfe = assertThrows(NotFoundException.class,
                () -> userService.updateUser(1, toSave));

        assertEquals("User not found", nfe.getMessage());
    }
}