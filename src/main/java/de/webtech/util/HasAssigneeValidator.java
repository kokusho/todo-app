package de.webtech.util;

import de.webtech.entities.Todo;
import de.webtech.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HasAssigneeValidator implements ConstraintValidator<HasValidAssignee, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String t, ConstraintValidatorContext constraintValidatorContext) {
        if(t == null || t.isEmpty()){
            return false;
        } else {
            //Assigned user is only valid if found in the user db
            return userRepository.findById(t).isPresent();
        }
    }
}
