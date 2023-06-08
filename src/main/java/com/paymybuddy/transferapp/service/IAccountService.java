package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAccounts();
    List<Account> getAccounts(int id);
    Account getAccount(int id);
    Account saveAccount(Account account);
    Account updateAccount(int id, Account account);
    void deleteAccount(int id);
}
