package com.autwind.blog.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private int commentId;
    private String nickName;
    private String content;
    private int blogId;
    private int parentId;


}
