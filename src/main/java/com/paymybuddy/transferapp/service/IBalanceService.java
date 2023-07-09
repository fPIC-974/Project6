package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;

import java.util.List;

public interface IBalanceService {
    List<Balance> getBalances();
    Balance getBalance(int id);
    Balance updateBalance(int id, Balance balance);
}
