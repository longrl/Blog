package com.autwind.blog.dto;


import com.autwind.blog.dto.validators.PasswordEqual;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@Builder
@PasswordEqual(min = 1, max = 6, message = "两次密码不一样")
public class PersonDTO {

    @Length(max = 4)
    public String name;
    private int age;
    private String password1;
    private String password2;

}
