package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService implements IBalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public List<Balance> getBalances() {
        return balanceRepository.findAll();
    }

    @Override
    public Balance getBalance(int id) {
        return balanceRepository.findById(id).orElse(null);
    }

    @Override
    public Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Balance updateBalance(int id, Balance balance) {
        Balance toUpdate = balanceRepository.findById(id).orElse(null);

        if (toUpdate != null && balance != null) {
            toUpdate.setAmount(balance.getAmount());
//            toUpdate.setUser(balance.getUser());

            return balanceRepository.save(toUpdate);
        }

        return null;
    }

    @Override
    public void deleteBalance(int id) {
        balanceRepository.deleteById(id);
    }
}
