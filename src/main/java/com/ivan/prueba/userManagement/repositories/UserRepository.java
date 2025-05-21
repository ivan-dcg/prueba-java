package com.ivan.prueba.userManagement.repositories;

import com.ivan.prueba.userManagement.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
