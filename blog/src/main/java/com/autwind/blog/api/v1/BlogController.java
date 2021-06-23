package com.autwind.blog.api.v1;

import com.autwind.blog.core.annotation.TokenRequired;
import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.exception.http.NotFoundException;
import com.autwind.blog.model.Blog;
import com.autwind.blog.service.BlogService;
import com.autwind.blog.service.TagService;
import com.autwind.blog.vo.ArchivesVO;
import com.autwind.blog.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {
    private TagService tagService;
    private BlogService service;
    @Autowired
    private void setService(BlogService service,TagService tagService){
        this.service = service;
        this.tagService = tagService;
    }

    @GetMapping()
    public Result getBlog(@RequestParam(defaultValue = "0") Integer page){
        List<BlogVO> list = service.getPageBlog(page,9);
        Result result =new Result();
        result.setData(list);
        return result;
    }

    @GetMapping("/{id}")
    public Result getBlogById(@PathVariable int id){
        Result result = new Result();
        try {
            BlogVO blog = service.findByBlogById(id);
            result.setData(blog);
            return result;
        }
        catch (IllegalArgumentException e){
            throw new NotFoundException(10001);
        }
    }


    @PostMapping("")
    public Result postBlog(@RequestBody Blog blog){
        service.PostBlog(blog);
        return new Result();
    }

    @TokenRequired
    @DeleteMapping("/{id}")
    public Result deleteBlogById(@PathVariable int id){
        service.deleteBlog(id);
        return new Result();
    }

    @TokenRequired
    @GetMapping("/latest")
    public Result getLatest(){
        Result result = new Result();
        result.setData(service.findOne());
        return result;
    }

    @GetMapping("/archives")
    public Result getArchives(@RequestParam(defaultValue = "0") Integer page){

        List<ArchivesVO> list = service.getPageBlogArchive(page,9);
        Result result = new Result();
        result.setData(list);
        return result;
    }

    @GetMapping("/latests")
    public Result getLatests(){
        Result result = new Result();
        result.setData(service.getLatest());
        return result;
    }

}
