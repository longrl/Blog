package com.autwind.blog.core;

import com.autwind.blog.core.configuration.ExceptionCodeConfiguration;
import com.autwind.blog.exception.http.HttpException;
import com.autwind.blog.exception.http.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnityResponse ExceptionHandler(HttpServletRequest req, Exception e){

        String method = req.getMethod();
        String url = req.getRequestURI();
        System.out.println(e);
        UnityResponse massage = new UnityResponse(9999,"服务器错误",method +" "+url);

        return massage;
    }


    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity HttpExceptionHandler(HttpServletRequest req, HttpException e){

        String method = req.getMethod();
        String url = req.getRequestURI();
        UnityResponse massage = new UnityResponse(e.getCode(),codeConfiguration.getMassage(e.getCode()),method +" "+url);
        System.out.println(codeConfiguration.getMassage(e.getCode()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        ResponseEntity<UnityResponse> r = new ResponseEntity<UnityResponse>(massage,httpHeaders,httpStatus);

        return r;
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public UnityResponse MethodArgumentExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e){

        String method = req.getMethod();
        String url = req.getRequestURI();
        System.out.println(e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);

        return new UnityResponse(10001, message,method + " " + url);
    }
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public UnityResponse ConstraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e){
        String method = req.getMethod();
        String url = req.getRequestURI();
        System.out.println(e);
        String message = e.getMessage();

        return new UnityResponse(10001, message,method + " " + url);

    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(';')
        );
        return errorMsg.toString();
    }
}
