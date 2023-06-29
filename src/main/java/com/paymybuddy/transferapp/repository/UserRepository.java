package com.paymybuddy.transferapp.repository;

import com.paymybuddy.transferapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> getUserByEmail(String email);
    User findByEmail(String email);
}
