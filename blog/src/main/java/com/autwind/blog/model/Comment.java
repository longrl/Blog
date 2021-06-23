package com.autwind.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Where(clause = "delete_time is null ")
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer blogId;
    private Integer parentId;

    private String content;
    private String email;
    private String nickname;

    @CreatedDate
    @Column(name = "create_time", insertable = false,updatable = false)
    private Date createTime;
    @Temporal(TemporalType.DATE)
    private Date deleteTime;

}
