package com.autwind.blog.service;

import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.model.Info;
import com.autwind.blog.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfoService {
    private InfoRepository repository;
    @Autowired
    public void setRepository(InfoRepository repository){
        this.repository = repository;
    }

    public Info getInfo(){
        Optional<Info> info = repository.findById(2);
        return info.get();
    }

    public void updateInfo(){

    }

    public void putInfo(){

    }


}
