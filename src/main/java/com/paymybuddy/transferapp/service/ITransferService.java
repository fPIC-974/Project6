package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Transfer;

import java.util.List;

public interface ITransferService {
    List<Transfer> getTransfers();
    Transfer getTransfer(int id);
    Transfer saveTransfer(Transfer transfer);
    Transfer updateTransfer(int id, Transfer transfer);
    void deleteTransfer(int id);
}
