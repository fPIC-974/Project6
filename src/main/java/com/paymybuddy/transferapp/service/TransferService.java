package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.Transfer;
import com.paymybuddy.transferapp.repository.AccountRepository;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService implements ITransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    public TransferService(TransferRepository transferRepository,
                           AccountRepository accountRepository,
                           BalanceRepository balanceRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
    }

    @Override
    public List<Transfer> getTransfers() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer getTransfer(int id) {
        return transferRepository.findById(id).orElse(null);
    }

    @Override
    public Transfer saveTransfer(Transfer transfer) {
        Balance balance = balanceRepository.findById(transfer.getBalance().getId()).orElse(null);
        balance.setAmount(balance.getAmount() + transfer.getAmount());
        balanceRepository.save(balance);
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer updateTransfer(int id, Transfer transfer) {
        return null;
    }

    @Override
    public void deleteTransfer(int id) {

    }
}
