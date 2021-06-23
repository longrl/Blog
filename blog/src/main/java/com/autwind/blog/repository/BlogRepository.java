package com.autwind.blog.repository;
import com.autwind.blog.model.Blog;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;


public interface BlogRepository extends PagingAndSortingRepository<Blog,Integer> {
    Blog findByBlogId(Integer id);
    List<Blog> findTop5ByTagId(Integer tag);
    
}
