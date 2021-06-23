package com.autwind.blog.service;

import com.autwind.blog.model.Blog;
import com.autwind.blog.repository.BlogRepository;

import com.autwind.blog.vo.ArchivesVO;
import com.autwind.blog.vo.BlogLatestVO;
import com.autwind.blog.vo.BlogVO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    private BlogRepository repository;
    private TagService tagService;
    @Autowired
    private void setRepository(BlogRepository repository){
        this.repository = repository;
    }
    @Autowired
    private void setTagService(TagService tagService){
        this.tagService = tagService;
    }

    public List<BlogVO> getPageBlog(int page, int size){

        List<Blog> list = repository.findAll(PageRequest.of(page,size, Sort.by(Sort.Order.desc("id")))).getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<BlogVO> vos = new ArrayList<>();

        list.forEach(s->{
            var tag = tagService.getTagById(s.getTagId());
            BlogVO vo = mapper.map(s,BlogVO.class);
            vo.setTag(tag.getTagName());
            vos.add(vo);
        });
        return vos;
    }

    public List<ArchivesVO> getPageBlogArchive(int page, int size){

        List<Blog> list = repository.findAll(PageRequest.of(page,size, Sort.by(Sort.Order.desc("id")))).getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<ArchivesVO> vos = new ArrayList<>();

        list.forEach(s->{

            ArchivesVO vo = mapper.map(s,ArchivesVO.class);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            vo.setCreateTime(formatter.format(s.getCreateTime()));

            vos.add(vo);
        });
        return vos;
    }

    public BlogVO findByBlogById(int id){
        BlogVO blogVO = new BlogVO();
        var blog = repository.findByBlogId(id);
        var tag = tagService.getTagById(blog.getTagId());
        BeanUtils.copyProperties(blog,blogVO);
        blogVO.setTag(tag.getTagName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        blogVO.setTime(formatter.format(blog.getCreateTime()));
        return blogVO;
    }

    public List<Blog> getBlogByTag(Integer tag){
        return repository.findTop5ByTagId(tag);
    }

    @Modifying
    @Transactional
    public void deleteBlog(int id){
        Blog blog = repository.findByBlogId(id);
        blog.setDeleteTime(new Date());
        repository.save(blog);
    }

    @Modifying
    @Transactional
    public void PostBlog(Blog blog){
        repository.save(blog);
    }

    public Blog findOne(){
        return repository.findAll(PageRequest.of(0,1, Sort.by(Sort.Order.desc("id")))).getContent().get(0);
    }


    public List<BlogLatestVO> getLatest(){
        List<Blog> list = repository.findAll(PageRequest.of(0,4, Sort.by(Sort.Order.desc("id")))).getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<BlogLatestVO> vos = new ArrayList<>();

        list.forEach(s->{

            BlogLatestVO vo = mapper.map(s,BlogLatestVO.class);
            vos.add(vo);
        });
        return vos;
    }

}
