package de.webtech.util;

import de.webtech.entities.Todo;
import de.webtech.entities.User;
import de.webtech.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HasAssigneeValidator implements ConstraintValidator<HasValidAssignee, User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            return false;
        } else {
            //Assigned user is only valid if found in the user db
            return userRepository.findById(user.getUsername()).isPresent();
        }
    }
}
