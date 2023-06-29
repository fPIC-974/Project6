package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.Account;
import com.paymybuddy.transferapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByName(String name);
    boolean existsByNumber(int number);

    List<Account> findAccountsByUser(User user);
}
