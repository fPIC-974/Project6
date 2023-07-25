package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Account;
import com.paymybuddy.transferapp.repository.AccountRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccounts(int id) {
        return null;
    }

    @Override
    public Account getAccount(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account saveAccount(Account account) {
        List<Account> accountList = accountRepository.findAccountsByUser(account.getUser());

        for (Account acc: accountList) {
            if (Objects.equals(acc.getName(), account.getName())
                    || Objects.equals(acc.getNumber(), account.getNumber())) {
                throw new RuntimeException("Already exists");
            }
        }

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(int id, Account account) {
        Account toUpdate = accountRepository.findById(id).orElse(null);

        if (toUpdate != null && account != null) {
            toUpdate.setName(account.getName());
            toUpdate.setUser(account.getUser());
            toUpdate.setNumber(account.getNumber());

            return accountRepository.save(toUpdate);
        }

        return null;
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }
}
