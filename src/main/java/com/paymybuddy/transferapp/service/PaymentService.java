package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Payment;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.PaymentRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public Page<Payment> getPaymentsPaginated(Pageable pageable, int id) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Payment> payments = getPaymentsByBalanceId(id);
        List<Payment> paymentList;

        if (payments.size() < startItem) {
            paymentList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, payments.size());
            paymentList = payments.subList(startItem, toIndex);
        }

        Page<Payment> paymentPage
                = new PageImpl<Payment>(paymentList, PageRequest.of(currentPage, pageSize), payments.size());

        return paymentPage;
    }

    @Override
    public Payment savePayment(Payment payment) {
        // Get the balance from origin user
        Balance sourceBalance = balanceRepository.findById(payment.getBalance().getId()).orElse(null);

        // Get the balance from destination user
        Balance destinationBalance = balanceRepository.findById(payment.getUser().getId()).orElse(null);

        if ((sourceBalance.getAmount() - payment.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        sourceBalance.setAmount(sourceBalance.getAmount() - payment.getAmount());
        destinationBalance.setAmount(destinationBalance.getAmount() + payment.getAmount());
        balanceRepository.save(sourceBalance);
        balanceRepository.save(destinationBalance);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(int id, Payment payment) {
        return null;
    }

    @Override
    public void deletePayment(int id) {

    }
}
