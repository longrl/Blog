package com.autwind.blog.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class )
public @interface PasswordEqual {

    int min() default 4;

    int max() default 6;

    String message() default "password is not Equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
