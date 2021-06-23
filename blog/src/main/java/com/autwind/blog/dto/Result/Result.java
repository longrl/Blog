package com.autwind.blog.dto.Result;

import lombok.Data;

@Data
public class Result {

    private Integer code =1;
    private String massage = "操作成功";
//    private Integer code;
//    private String massage;
    private Object data;
}
