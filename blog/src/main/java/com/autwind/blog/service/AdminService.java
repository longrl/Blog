package com.autwind.blog.service;

import com.autwind.blog.model.Info;
import com.autwind.blog.repository.BlogRepository;
import com.autwind.blog.repository.CommentRepository;
import com.autwind.blog.repository.InfoRepository;
import com.autwind.blog.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    private CommentRepository commentRepository;
    private BlogRepository blogRepository;
    private InfoRepository infoRepository;
    @Autowired
    public void setCommentRepositoryAndBlogRepository(CommentRepository commentRepository,
                                                      BlogRepository blogRepository,
                                                      InfoRepository infoRepository){
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.infoRepository = infoRepository;
    }

    public AdminVO getInfo(){
        AdminVO vo = new AdminVO();
        List blogList = new ArrayList();
        blogRepository.findAll().forEach(s->{
            blogList.add(s);
        });
        vo.setBlogCount(blogList.size());

        List commentList = new ArrayList();
        vo.setCommentCount((int)commentRepository.count());

        Optional<Info> info = infoRepository.findById(2);
        if(!info.isEmpty()) vo.setAvatar(info.get().getAvatar());

        return vo;
    }




}
