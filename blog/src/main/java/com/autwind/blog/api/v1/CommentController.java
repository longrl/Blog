package com.autwind.blog.api.v1;


import com.autwind.blog.core.annotation.TokenRequired;
import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.model.Comment;
import com.autwind.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private CommentService service;
    @Autowired
    public void setService(CommentService service){
        this.service = service;
    }

    @GetMapping(value = "/{blogId}")
    public Result getCommentsById(@PathVariable Integer blogId){
        return service.getCommentByBlogId(blogId);
    }

    @PostMapping(value = "")
    public Result putComment(@RequestBody Comment comment){
        if(comment.getParentId() == null) comment.setParentId(-1);
        service.putComment(comment);
        return new Result();
    }

    @GetMapping(value = "")
    public Result getComments(@RequestParam int current, int size){
        var commentList = service.getComments(current-1,size);
        Result result =new Result();
        result.setData(commentList);
        return  result;
    }

    @TokenRequired
    @DeleteMapping(value = "/{id}")
    public Result deleteComment(@PathVariable Integer id){
        service.deleteComment(id);
        return new Result();
    }


}
