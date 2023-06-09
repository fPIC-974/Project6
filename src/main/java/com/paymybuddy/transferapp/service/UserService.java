package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import lombok.Data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;

    public UserService(UserRepository userRepository,
                       BalanceRepository balanceRepository) {
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
    }

    /**
     * Get a list of users
     *
     * @return the list of User objects
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        User toAdd = userRepository.save(user);
        Balance balance = new Balance(toAdd);
        toAdd.setBalance(balance);
        balanceRepository.save(balance);

        return toAdd;
    }

    @Override
    public User updateUser(int id, User user) {
        User toUpdate = userRepository.findById(id).orElse(null);

        if (toUpdate != null && user != null) {
            toUpdate.setLastName(user.getLastName());
            toUpdate.setFirstName(user.getFirstName());
            toUpdate.setEmail(user.getEmail());
            toUpdate.setPassword(user.getPassword());

            return userRepository.save(toUpdate);
        }

        return null;
    }

    @Override
    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}
