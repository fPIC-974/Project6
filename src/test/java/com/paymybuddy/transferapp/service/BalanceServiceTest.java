package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    public void getNoBalances() {
        when(balanceRepository.findAll()).thenReturn(new ArrayList<>());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> balanceService.getBalances());

        assertEquals("No balances found", runtimeException.getMessage());
    }

    @Test
    public void getBalances() {
        List<Balance> balanceList = new ArrayList<>();
        balanceList.add(new Balance());
        balanceList.add(new Balance());

        when(balanceRepository.findAll()).thenReturn(balanceList);

        List<Balance> toCheck = balanceService.getBalances();

        assertEquals(2, balanceList.size());
    }

    @Test
    public void getBalanceInvalidId() {
        when(balanceRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> balanceService.getBalance(1));

        assertEquals("Balance not found", notFoundException.getMessage());
    }

    @Test
    public void getBalanceValidId() {
        Balance balance = new Balance();
        balance.setAmount(100.00);

        when(balanceRepository.findById(anyInt())).thenReturn(Optional.of(balance));

        Balance toCheck = balanceService.getBalance(1);

        assertEquals(100.00, toCheck.getAmount());
    }

    @Test
    public void updateInvalidParametersBalance() {
        when(balanceRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> balanceService.updateBalance(1, null));

        assertEquals("Invalid parameters", runtimeException.getMessage());
    }

    @Test
    public void updateBalance() {
        User user = new User("toto", "TOTO", "toto@mail.net", "pass");
        user.setId(1);

        Balance toUpdate = new Balance();
        toUpdate.setAmount(0.00);
        toUpdate.setId(1);
        toUpdate.setUser(user);
        toUpdate.setTransfers(new ArrayList<>());
        toUpdate.setTransfers(new ArrayList<>());

        user.setBalance(toUpdate);

        Balance toSave = new Balance();
        toSave.setAmount(100.00);

        when(balanceRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(balanceRepository.save(toUpdate)).thenReturn(toSave);

        Balance toCheck = balanceService.updateBalance(1, toSave);

        assertNotNull(toCheck);
        assertEquals(100.00, toUpdate.getAmount());
        assertEquals(100.00, toCheck.getAmount());
    }
}