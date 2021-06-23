package com.autwind.blog.api.v1;

import com.autwind.blog.core.annotation.TokenRequired;
import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.model.User;
import com.autwind.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService service;
    @Autowired
    public void setService(UserService service){
        this.service = service;
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String token = service.login(user.getName(),user.getPassword());
        var result = new Result();
        result.setData(token);
        return result;
    }

    @TokenRequired
    @GetMapping(value = "/test")
    public String test(){
        return "认证成功";
    }

}
