package com.autwind.blog.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentVO {

    private Integer id;
    private Integer parentId;

    private String time;
    private String nickname;
    private String content;

    private String pNickname;
    private String parentContent;


}
