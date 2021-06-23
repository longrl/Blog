package com.autwind.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Info {
    @Id
    private Integer id;

    private String name;
    private String content;
    private String avatar;

}
