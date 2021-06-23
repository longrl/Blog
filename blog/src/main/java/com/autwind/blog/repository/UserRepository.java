package com.autwind.blog.repository;

import com.autwind.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByName(String name);
    public Optional<User> findById(Integer id);
}
