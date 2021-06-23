package com.autwind.blog.service;

import com.autwind.blog.exception.http.NotFoundException;
import com.autwind.blog.model.User;
import com.autwind.blog.repository.UserRepository;
import com.autwind.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;
    @Autowired
    public void setRepository(UserRepository repository){
        this.repository = repository;
    }

    public String login(String name, String password) {
        String token = null;
        try {
            //校验用户是否存在
            User user = repository.findByName(name);
            if(user == null){
                throw new NotFoundException(10001);
            }else{
                //检验用户密码是否正确
                if(!user.getPassword().equals(password)){
                    throw new NotFoundException(10004);
                }else {
                    // 生成token，将 user id 、userName保存到 token 里面
                    token = JwtUtil.sign(user.getName(),user.getPassword());
                }
            }
        } catch (Exception e) {
            throw new NotFoundException(10005);
        }
        return token;
    }

}
