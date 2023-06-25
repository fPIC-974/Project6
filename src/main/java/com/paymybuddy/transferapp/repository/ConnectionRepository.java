package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.Connection;
import com.paymybuddy.transferapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    public List<Connection> getConnectionsByUserId(Integer id);
}
