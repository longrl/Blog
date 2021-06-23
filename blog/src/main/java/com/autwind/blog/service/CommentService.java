package com.autwind.blog.service;

import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.exception.http.NotFoundException;
import com.autwind.blog.model.Blog;
import com.autwind.blog.model.Comment;
import com.autwind.blog.repository.BlogRepository;
import com.autwind.blog.repository.CommentRepository;
import com.autwind.blog.vo.CommentListVO;
import com.autwind.blog.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository repository;
    private BlogRepository blogRepository;
    @Autowired
    public void setRepository(CommentRepository repository, BlogRepository blogRepository){
        this.repository = repository;
        this.blogRepository = blogRepository;
    }

    public Result getCommentByBlogId(int id){

        Specification<Comment> specification = new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> Id = root.get("blogId");
                Predicate predicate = criteriaBuilder.equal(Id, id);
                return predicate;
            }
        };

        List<Comment> list = repository.findAll(specification,Sort.by(Sort.Order.desc("createTime")));
        List<CommentVO> commentVOS = new ArrayList<>();
        list.forEach(s->{
            CommentVO commentVO =new CommentVO();
            if(s.getParentId() != -1 & s.getParentId()!=null){
                Optional<Comment> comment = repository.findById(s.getParentId());
                commentVO.setParentContent(comment.get().getContent());
                commentVO.setParentId(comment.get().getId());
                commentVO.setPNickname(comment.get().getNickname());
                commentVO.setContent(s.getContent());
                commentVO.setParentId(s.getParentId());

            }
            BeanUtils.copyProperties(s,commentVO);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            commentVO.setTime(formatter.format(s.getCreateTime()));
            commentVOS.add(commentVO);
        });

        Result result = new Result();
        result.setData(commentVOS);

        return result;
    }

    @Modifying
    @Transactional
    public Result putComment(Comment comment){
        repository.save(comment);
        return new Result();
    }

    public List<CommentListVO> getComments(int page, int size){
        var commentList = repository.findAll(PageRequest
                .of(page,size, Sort.by(Sort.Order.desc("id")))).getContent();
        List<CommentListVO> vos = new ArrayList<>();
        commentList.forEach(s->{
            CommentListVO vo = new CommentListVO();
            try{
                var blog = blogRepository.findByBlogId(s.getBlogId());
                vo.setBlogTitle(blog.getTitle());
            }catch (Exception e){
                throw new NotFoundException(10001);
            }

            if(s.getParentId() != -1){
                var comment =repository.findById(s.getParentId());

                if(!comment.isEmpty()){
                    vo.setParentContent(comment.get().getContent());
                }

            }else {
                vo.setParentContent("无");
            }
            vo.setNickName(s.getNickname());
            vo.setCreateTime(s.getCreateTime());
            vo.setCommentContent(s.getContent());
            vo.setId(s.getId());
            vos.add(vo);
        });

        return vos;
    }

    @Modifying
    @Transactional
    public void deleteComment(int id){
        Comment comment = repository.findById(id).get();
        comment.setDeleteTime(new Date());
        repository.save(comment);
    }

}
