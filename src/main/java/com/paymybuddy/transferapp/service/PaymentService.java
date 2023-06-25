package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.PaymentRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    private PaymentRepository paymentRepository;
    private BalanceRepository balanceRepository;

    private UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          BalanceRepository balanceRepository,
                          UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPayment(int id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> getPaymentsByBalanceId(int id) {
        return paymentRepository.getPaymentsByBalance(balanceRepository.findById(id).orElse(null));
    }

    @Override
    public Payment savePayment(Payment payment) {
        // Get the balance from origin user
        Balance sourceBalance = balanceRepository.findById(payment.getBalance().getId()).orElse(null);

        // Get the balance from destination user
        Balance destinationBalance = balanceRepository.findById(payment.getUser().getId()).orElse(null);

        if ((sourceBalance.getAmount() - payment.getAmount()) >= 0) {
            sourceBalance.setAmount(sourceBalance.getAmount() - payment.getAmount());
            destinationBalance.setAmount(destinationBalance.getAmount() + payment.getAmount());
            balanceRepository.save(sourceBalance);
            balanceRepository.save(destinationBalance);
            return paymentRepository.save(payment);
        }

        return null;
    }

    @Override
    public Payment updatePayment(int id, Payment payment) {
        return null;
    }

    @Override
    public void deletePayment(int id) {

    }
}
