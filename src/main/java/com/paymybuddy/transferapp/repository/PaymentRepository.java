package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> getPaymentsByUser(User user);
    List<Payment> getPaymentsByBalance(Balance balance);
}
