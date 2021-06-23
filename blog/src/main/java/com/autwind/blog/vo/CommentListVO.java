package com.autwind.blog.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentListVO {

    private Integer id;

    private String nickName;
    private String parentContent;
    private String blogTitle;
    private String commentContent;
    private Date createTime;

}
