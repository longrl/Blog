package com.autwind.blog.api.v1;

import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/webInfo")
public class InfoController {
    private InfoService service;
    @Autowired
    public void setService(InfoService service){
        this.service = service;
    }
    @GetMapping(value = "")
    public Result getInfo(){
        Result result =new Result();
        result.setData(service.getInfo());
        return result;
    }


}
