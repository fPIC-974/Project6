package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User getUserById(int id);

    User saveUser(User user);

    User updateUser(int id, User user);

    void deleteUser(int id);
}
