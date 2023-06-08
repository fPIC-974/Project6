package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.UserRepository;
import lombok.Data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get a list of users
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
        return userRepository.save(user);
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
        userRepository.deleteById(id);
    }
}
