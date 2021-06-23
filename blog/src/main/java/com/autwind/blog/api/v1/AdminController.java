package com.autwind.blog.api.v1;


import com.autwind.blog.core.annotation.TokenRequired;
import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    private AdminService service;
    @Autowired
    public void setService(AdminService service){
        this.service = service;
    }

    @GetMapping(value = "/info")
    public Result getInfo(){
        Result result = new Result();
        result.setData(service.getInfo());
        return result;
    }
}
