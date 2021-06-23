package com.autwind.blog.repository;

import com.autwind.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface CommentRepository extends PagingAndSortingRepository<Comment,Integer>, JpaSpecificationExecutor<Comment> {
    public Optional<Comment> findById(Integer id);
}
