package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.model.Connection;
import com.paymybuddy.transferapp.model.User;

import java.util.List;

public interface IConnectionService {
    List<Connection> getConnections();
    Connection getConnectionsById(int id);
    Connection saveConnection(User owner, User related);
}
