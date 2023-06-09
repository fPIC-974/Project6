package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
}
