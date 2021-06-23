package com.autwind.blog.service;

import com.autwind.blog.model.Tag;
import com.autwind.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagService {
    private TagRepository repository;

    @Autowired
    public void setRepository(TagRepository repository){
        this.repository = repository;
    }

    public List<Tag> getTags(){

        List<Tag> list = new ArrayList<>();
        Iterable<Tag> tags = repository.findAll();
        tags.forEach(s->{
            list.add(s);
        });
        return list;

    }

    public Tag getTagById(int id){
        var tag =repository.findById(id);
        return tag;
    }

    public void postTag(Tag tag){
        repository.save(tag);
    }


}
