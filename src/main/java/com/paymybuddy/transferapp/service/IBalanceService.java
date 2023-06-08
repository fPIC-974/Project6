package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;

import java.util.List;

public interface IBalanceService {
    List<Balance> getBalances();
    Balance getBalance(int id);
    Balance saveBalance(Balance balance);
    Balance updateBalance(int id, Balance balance);
    void deleteBalance(int id);
}
