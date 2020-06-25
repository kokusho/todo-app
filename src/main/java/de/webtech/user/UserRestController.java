package de.webtech.user;

import de.webtech.entities.AssigneeList;
import de.webtech.entities.User;
import de.webtech.shiro.SecurityUtilsWrapper;
import de.webtech.util.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {
    private static final transient Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    SecurityUtilsWrapper securityUtilsWrapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Validator validator;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user){
        Optional<User> userByIdOpt = userRepository.findById(user.getUsername());
        if(userByIdOpt.isPresent()){
            return new ResponseEntity(new ResponseMessage(Collections.singleton("Username is already taken!")), HttpStatus.BAD_REQUEST);
        }
        Set<ConstraintViolation<@Valid User>> violations = validator.validate(user);
        if( !violations.isEmpty() ){
            return new ResponseEntity(new ResponseMessage(violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet())), HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        log.info("Successfully registered a new user: " + user.getUsername());
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user, @RequestParam(name = "rememberMe", defaultValue = "false") Boolean rememberMe){
        Set<ConstraintViolation<@Valid User>> violations = validator.validate(user);
        if( !violations.isEmpty() ){
            return new ResponseEntity(new ResponseMessage(violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet())), HttpStatus.BAD_REQUEST);
        }
        try {
            Subject currentUser = securityUtilsWrapper.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            token.setRememberMe(rememberMe.booleanValue());

            currentUser.login(token);
            //TODO add error handling
            log.info("Successfully logged in user: " + token.getUsername());
            return new ResponseEntity<>(new User(token.getUsername(), new String(token.getPassword())), HttpStatus.OK);
        } catch (AuthenticationException e){
            log.error("User " + user.getUsername() + " could not be logged in!" + e);
            return new ResponseEntity<>("User could not be logged in!", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logoutUser(){
        Subject currentUser = securityUtilsWrapper.getSubject();
        log.info("User logged out: " + currentUser.getPrincipal().toString());
        currentUser.logout();
        return new ResponseEntity<>("User logged out!", HttpStatus.OK);
    }

    @GetMapping("/potentialAssignees")
    public AssigneeList getPotentialAssignees(){
        Set<String> names = new HashSet<>();
        for (User u : userRepository.findAll()) {
            names.add(u.getUsername());
        }
        names.remove(securityUtilsWrapper.getPrincipal());
        return new AssigneeList(names);
    }

    //FIXME for debug purposes only
    @GetMapping("/whoami")
    public ResponseEntity<Object> whoami(){
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setUsername((String) subject.getPrincipal());
        if(subject.isAuthenticated())
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("No user is logged in!", HttpStatus.UNAUTHORIZED);
    }
}
