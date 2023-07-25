package com.paymybuddy.transferapp.config;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.PaymentRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import com.paymybuddy.transferapp.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Test
    public void loadUserByUserNameInvalid() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> customUserDetailService.loadUserByUsername("whoever"));

        assertEquals("Invalid credentials", notFoundException.getMessage());
    }

    @Test
    public void loadUserByUserNameValid() {
        User user = new User("toto", "TOTO", "toto@mail.net", "pass");
        user.setRole("USER");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getEmail());

        assertEquals("toto@mail.net", userDetails.getUsername());
        assertEquals("pass", userDetails.getPassword());
        assertEquals("[USER]", userDetails.getAuthorities().toString());
    }
}