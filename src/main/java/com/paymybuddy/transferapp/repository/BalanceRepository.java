package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
}
