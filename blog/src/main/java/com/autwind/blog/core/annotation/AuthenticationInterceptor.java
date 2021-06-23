package com.autwind.blog.core.annotation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.autwind.blog.exception.http.TokenException;
import com.autwind.blog.model.User;
import com.autwind.blog.repository.UserRepository;
import com.autwind.blog.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private UserRepository repository;
    @Autowired
    public void setRepository(UserRepository repository){
        this.repository =repository;
    }
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(TokenRequired.class)) {
            TokenRequired userLoginToken = method.getAnnotation(TokenRequired.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new TokenException(20004);
                }
                // 获取 token 中的 user id
                String name;
                try {
                    name = JWT.decode(token).getClaim("name").asString();
                } catch (JWTDecodeException j) {
                    throw new TokenException(20001);
                }
                User user = repository.findByName(name);
                if (user == null) {
                    throw new TokenException(20002);
                }
                // 验证 token
                try {
                    if(!JwtUtil.verity(token,user.getPassword())){
                        throw new TokenException(20003);
                    }
                } catch (JWTVerificationException e) {
                    throw new TokenException(20001);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
