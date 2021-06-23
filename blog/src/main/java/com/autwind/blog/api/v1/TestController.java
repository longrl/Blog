package com.autwind.blog.api.v1;

import com.autwind.blog.exception.http.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/")
@Validated
public class TestController {


    @GetMapping(value = "/test")
    public String test(){

        throw new NotFoundException(10001);

    }

}
