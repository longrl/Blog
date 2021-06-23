package com.autwind.blog.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogVO {

    private Integer id;
    private Integer tagId;

    private String tag;
    private String content;
    private String html;
    private String likenum;
    private String readnum;
    private String time;
    private String title;
    private String images;
    private Integer blogId;

}
