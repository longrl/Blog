package com.autwind.blog.repository;

import com.autwind.blog.model.Info;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InfoRepository extends CrudRepository<Info, Integer> {
    public Optional<Info> findById(Integer id);
}
