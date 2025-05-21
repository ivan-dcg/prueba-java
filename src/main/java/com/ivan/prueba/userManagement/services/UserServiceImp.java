package com.ivan.prueba.userManagement.services;

import com.ivan.prueba.userManagement.entities.User;
import com.ivan.prueba.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            User userDb = userOptional.orElseThrow();

            userDb.setUsername(user.getUsername());
            userDb.setPassword(user.getPassword());
            return Optional.of(repository.save(userDb));

        }
        return userOptional;
    }

    @Override
    @Transactional
    public Optional<User> delete(Long id) {
        Optional<User> userOptional = repository.findById(id);
        userOptional.ifPresent(userDB -> {
            repository.delete(userDB);
        });
        return userOptional;
    }
}
