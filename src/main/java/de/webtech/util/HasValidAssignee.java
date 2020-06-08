package de.webtech.util;

import de.webtech.entities.Todo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = HasAssigneeValidator.class)
@Documented
public @interface HasValidAssignee {
    String message() default "The assigned user is invalid!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
