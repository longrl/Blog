package com.autwind.blog.repository;

import com.autwind.blog.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TagRepository extends CrudRepository<Tag,Integer> {
    public Tag findById(int id);
}
