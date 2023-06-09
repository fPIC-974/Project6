package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPaymentService {
    List<Payment> getPayments();
    Payment getPayment(int id);
    Payment savePayment(Payment payment);
    Payment updatePayment(int id, Payment payment);
    void deletePayment(int id);
}
