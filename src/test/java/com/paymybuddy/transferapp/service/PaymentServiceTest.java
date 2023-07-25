package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.PaymentRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void getNoPayments() {
        when(paymentRepository.findAll()).thenReturn(null);

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> paymentService.getPayments());

        assertEquals("No payments found", notFoundException.getMessage());
    }

    @Test
    public void getPayments() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> paymentService.getPayments());
    }

    @Test
    public void getPaymentByInvalidId() {
        when(paymentRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> paymentService.getPayment(1));

        assertEquals("No payment found", notFoundException.getMessage());
    }

    @Test
    public void getPayment() {
        when(paymentRepository.findById(anyInt())).thenReturn(Optional.ofNullable(new Payment()));

        assertDoesNotThrow(() -> paymentService.getPayment(1));
    }

    @Test
    public void getNonExistingPaymentsByBalanceId() {
        when(balanceRepository.existsById(anyInt())).thenReturn(false);

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> paymentService.getPaymentsByBalanceId(1));

        assertEquals("Balance not found", notFoundException.getMessage());
    }

    @Test
    public void getPaymentsByBalanceId() {
        when(balanceRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> paymentService.getPaymentsByBalanceId(1));
    }

    @Test
    public void getPaymentsPaginatedEmptyListOfPayments() {
        List<Payment> payments = new ArrayList<>();

        when(balanceRepository.existsById(anyInt())).thenReturn(true);
        when(balanceRepository.findById(anyInt())).thenReturn(Optional.of(new Balance()));
        when(paymentRepository.getPaymentsByBalance(any(Balance.class))).thenReturn(payments);

        Page<Payment> toCheck = paymentService.getPaymentsPaginated(PageRequest.of(0, 3), 1);

        assertEquals(0, payments.size());
        assertEquals(0, toCheck.getTotalElements());
        assertEquals(0, toCheck.getTotalPages());
        assertEquals(Collections.emptyList(), toCheck.getContent());
        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getPaymentsPaginatedListOfPaymentsOnePage() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment());
        payments.add(new Payment());
        payments.add(new Payment());

        when(balanceRepository.existsById(anyInt())).thenReturn(true);
        when(balanceRepository.findById(anyInt())).thenReturn(Optional.of(new Balance()));
        when(paymentRepository.getPaymentsByBalance(any(Balance.class))).thenReturn(payments);

        Page<Payment> toCheck = paymentService.getPaymentsPaginated(PageRequest.of(0, 3), 1);

        assertEquals(3, payments.size());
        assertEquals(3, toCheck.getTotalElements());
        assertEquals(1, toCheck.getTotalPages());
    }

    @Test
    public void getPaymentsPaginatedListOfPaymentsMultiPage() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment(100.00, "One", new Balance(), new User()));
        payments.add(new Payment());
        payments.add(new Payment());
        payments.add(new Payment());

        when(balanceRepository.existsById(anyInt())).thenReturn(true);
        when(balanceRepository.findById(anyInt())).thenReturn(Optional.of(new Balance()));
        when(paymentRepository.getPaymentsByBalance(any(Balance.class))).thenReturn(payments);

        Page<Payment> toCheck = paymentService.getPaymentsPaginated(PageRequest.of(0, 3), 1);

        assertEquals(4, payments.size());
        assertEquals(4, toCheck.getTotalElements());
        assertEquals(2, toCheck.getTotalPages());
        assertEquals(3, toCheck.getContent().size());
        assertEquals(100.00, toCheck.getContent().get(0).getAmount());
        assertEquals("One", toCheck.getContent().get(0).getDescription());
        assertEquals(payments.subList(0, 3), toCheck.getContent());
    }

    @Test
    public void notSavingPaymentWithInsufficientFunds() {
        User userSource = new User("toto", "TOTO", "toto@mail.net", "pass");
        userSource.setId(1);

        User userDestination = new User("titi", "TITI", "titi@mail.net", "pass");
        userDestination.setId(2);

        Balance sourceBalance = new Balance();
        sourceBalance.setId(1);
        sourceBalance.setUser(userSource);
        sourceBalance.setAmount(0.00);

        Balance destinationBalance = new Balance();
        destinationBalance.setId(2);
        destinationBalance.setUser(userDestination);
        destinationBalance.setAmount(0.00);

        Payment payment = new Payment(100.00, "Whatever", sourceBalance, userDestination);
        payment.setBalance(sourceBalance);
        payment.setUser(userDestination);

        when(balanceRepository.findById(1)).thenReturn(Optional.of(sourceBalance));
        when(balanceRepository.findById(2)).thenReturn(Optional.of(destinationBalance));

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> paymentService.savePayment(payment));

        assertEquals("Insufficient funds", runtimeException.getMessage());
    }

    @Test
    public void savingPayment() {
        User userSource = new User("toto", "TOTO", "toto@mail.net", "pass");
        userSource.setId(1);

        User userDestination = new User("titi", "TITI", "titi@mail.net", "pass");
        userDestination.setId(2);

        Balance sourceBalance = new Balance();
        sourceBalance.setId(1);
        sourceBalance.setUser(userSource);
        sourceBalance.setAmount(200.00);

        Balance destinationBalance = new Balance();
        destinationBalance.setId(2);
        destinationBalance.setUser(userDestination);
        destinationBalance.setAmount(0.00);

        Payment payment = new Payment(100.00, "Whatever", sourceBalance, userDestination);
        payment.setBalance(sourceBalance);
        payment.setUser(userDestination);

        when(balanceRepository.findById(1)).thenReturn(Optional.of(sourceBalance));
        when(balanceRepository.findById(2)).thenReturn(Optional.of(destinationBalance));

        paymentService.savePayment(payment);
        assertEquals(100.00, sourceBalance.getAmount());
        assertEquals(100.00, destinationBalance.getAmount());
    }
}