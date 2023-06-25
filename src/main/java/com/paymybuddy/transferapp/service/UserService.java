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

    /**
     * Get a User identified by its id field in database
     * @param id the id of the requested user
     * @return the requested User object
     */
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    /**
     * Save a User in the database
     * A Balance is also created in database for this User
     * @param user the user to be saved
     * @return the User saved in database
     */
    @Override
    public User saveUser(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            User toAdd = userRepository.save(user);
            Balance balance = new Balance(toAdd);
            toAdd.setBalance(balance);
            balanceRepository.save(balance);

            return toAdd;
        }

        return null;
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
