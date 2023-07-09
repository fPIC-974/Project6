package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService implements IBalanceService {
    private static final Logger logger = LogManager.getLogger(BalanceService.class);

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public List<Balance> getBalances() {
        logger.debug("Method called : getBalances()");

        List<Balance> balanceList = balanceRepository.findAll();

        if (balanceList.isEmpty()) {
            logger.error("No balances found");
            throw new RuntimeException("No balances found");
        }

        return balanceList;
    }

    @Override
    public Balance getBalance(int id) {
        logger.debug("Method called : getBalances(" + id + ")");

        Balance balance = balanceRepository.findById(id).orElse(null);

        if (balance == null) {
            logger.error("Could not find Balance with id : " + id);
            throw new NotFoundException("Balance not found");
        }

        return balance;
    }

    @Override
    public Balance updateBalance(int id, Balance balance) {
        logger.debug("Method called : updateBalance(" + id + ", " + balance + ")");

        Balance toUpdate = balanceRepository.findById(id).orElse(null);

        if (toUpdate == null || balance == null) {
            logger.error("Invalid parameters");
            throw new RuntimeException("Invalid parameters");
        }

        toUpdate.setAmount(balance.getAmount());
//            toUpdate.setUser(balance.getUser());

        return balanceRepository.save(toUpdate);
    }

}
