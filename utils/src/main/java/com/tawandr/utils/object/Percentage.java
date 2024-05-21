package com.tawandr.utils.object;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@DecimalMin("0")
@DecimalMax("1")
public @interface Percentage {
    String message() default "Invalid percentage value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}