package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
