package org.example.web.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegexValidation.class)
public @interface RegexValidationAnnotation {
    String message() default "Regex incorrect";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
