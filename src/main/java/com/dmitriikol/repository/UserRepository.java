package com.dmitriikol.repository;

import com.dmitriikol.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
