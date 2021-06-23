package com.autwind.blog.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name="blog")
@Where(clause = "delete_time is null ")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tagId;

    private String content;
    private String html;
    private String likenum;
    private String readnum;

    private Date time;

    private String title;
    private String images;
    private Integer blogId;

    @CreatedDate
    @Column(name = "create_time", insertable = false,updatable = false)
    private Date createTime;

    private Date updateTime;
    private Date deleteTime;


}
