package com.autwind.blog.api.v1;

import com.autwind.blog.core.annotation.TokenRequired;
import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.model.Tag;
import com.autwind.blog.service.BlogService;
import com.autwind.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/tag")
public class TagController {

    private BlogService service;
    @Autowired
    private void setService(BlogService service){
        this.service = service;
    }

    private TagService tagService;
    @Autowired
    private void setTagService(TagService tagService){
        this.tagService = tagService;
    }


    @GetMapping(value = "")
    public Result getAllTag(){
        Result result =new Result();
        result.setData(tagService.getTags());
        return result;
    }

    @GetMapping(value = "/{tag}")
    public Result getBlogByTag(@PathVariable Integer tag){

       Result result =new Result();
       result.setData(service.getBlogByTag(tag));
       return result;
    }

    @TokenRequired
    @PostMapping(value = "")
    public Result postTag(@RequestBody Tag tag){
        tagService.postTag(tag);
        return new Result();
    }



}
