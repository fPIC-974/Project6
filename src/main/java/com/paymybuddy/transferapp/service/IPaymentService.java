package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPaymentService {
    List<Payment> getPayments();
    Payment getPayment(int id);
    Payment savePayment(Payment payment);
    List<Payment> getPaymentsByBalanceId(int id);
    Page<Payment> getPaymentsPaginated(Pageable pageable, int id);
}
