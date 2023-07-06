package com.paymybuddy.transferapp.service;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.Connection;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.ConnectionRepository;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConnectionService implements IConnectionService {

    private final Logger logger = LogManager.getLogger(ConnectionService.class);

    private final ConnectionRepository connectionRepository;

    private final UserRepository userRepository;

    public ConnectionService(ConnectionRepository connectionRepository,
                             UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Connection> getConnections() {
        logger.debug("Method called : getConnections()");

        List<Connection> connectionList = connectionRepository.findAll();

        if (connectionList == null) {
            throw new RuntimeException("No connections found");
        }

        return connectionList;
    }

    @Override
    public Connection getConnectionsById(int id) {
        logger.debug("Method called : getConnectionById(" + id + ")");

        Optional<Connection> connection = connectionRepository.findById(id);
        logger.debug("Method called : connectionRepository.findById(" + id + ")");

        if (connection.isEmpty()) {
            logger.error("Connection with id " + id + " not found");
            throw new NotFoundException("Connection not found");
        }

        return connection.get();
    }

    @Override
    public List<User> getConnectionsByUserId(int id) {
        logger.debug("Method called : getConnectionsByUserId(" + id + ")");

        List<User> connections = new ArrayList<>();

        if (!userRepository.existsById(id)) {
            logger.error(("User with id " + id + " not found"));
            throw new NotFoundException("User not found");
        }

        for(Connection connection: connectionRepository.getConnectionsByUserId(id)) {
            connections.add(userRepository.findById(connection.getConnectionId()).orElse(null));
        }

        return connections;
    }

    @Override
    public Connection saveConnection(User owner, User related) {
        logger.debug("Method called : saveConnection(" + owner + ", " + related + ")");

        if (owner == null || related == null) {
            logger.error("Invalid parameters");
            throw new NullPointerException("Invalid parameters");
        }

        if (userRepository.findById(related.getId()).isEmpty()) {
            logger.error("Connection with id " + related.getId() + " not found");
            throw new NotFoundException("User not found");
        }

        return connectionRepository.save(new Connection(owner.getId(), related.getId()));
    }
}
