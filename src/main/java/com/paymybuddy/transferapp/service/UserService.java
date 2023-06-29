package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.AlreadyExistsException;
import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Balance;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.BalanceRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import lombok.Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

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
        logger.debug("Method called : getUsers()");
        return userRepository.findAll();
    }

    /**
     * Get a User identified by its id field in database
     * @param id the id of the requested user
     * @return the requested User object
     */
    @Override
    public User getUserById(int id) {
        logger.debug("Method called : getUserById(" + id + ")");

        logger.debug("Method called : userRepository.findById(" + id + ")");
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            logger.error("User with id " + id + " not found");
            throw new NotFoundException("User not found");
        }

        return user.get();
    }

    @Override
    public User getUserByEmail(String email) {
        logger.debug("Method called : getUserByEmail(" + email + ")");

        logger.debug("Method called : userRepository.getUserByEmail(" + email + ")");
                Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isEmpty()) {
            logger.error("User with email " + email + " not found");
            throw new NotFoundException("User not found");
        }

        return user.get();
    }

    /**
     * Save a User in the database
     * A Balance is also created in database for this User
     * @param user the user to be saved
     * @return the User saved in database
     */
    @Override
    public User saveUser(User user) {
        logger.debug("Method called : saveUser(" + user + ")");

        logger.debug("Method called : userRepository.existsByEmail(" + user.getEmail() + ")");
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("User with email " + user.getEmail() + " already exists");
            throw new AlreadyExistsException("User already exists");
        }

        logger.debug("Method called : balanceRepository.existsById(" + user.getId() + ")");
        if (balanceRepository.existsById(user.getId())) {
            logger.error("Balance with id " + user.getId() + " already exists");
            throw new AlreadyExistsException("Balance already exists");
        }

        logger.debug("Method called : userRepository.save(" + user + ")");
        User toAdd = userRepository.save(user);

        Balance balance = new Balance(toAdd);
        toAdd.setBalance(balance);

        logger.debug("Method called : balanceRepository.save(" + balance + ")");
        balanceRepository.save(balance);

        return toAdd;
    }

    @Override
    public User updateUser(int id, User user) {

        if (user == null) {
            throw new NullPointerException();
        }

        User toUpdate = userRepository.findById(id).orElse(null);

        if (toUpdate == null) {
            throw new NotFoundException("User not found");
        }

        toUpdate.setLastName(user.getLastName());
        toUpdate.setFirstName(user.getFirstName());
        toUpdate.setEmail(user.getEmail());
        toUpdate.setPassword(user.getPassword());

        return userRepository.save(toUpdate);
    }

    @Override
    public void deleteUser(int id) {

    }
}
