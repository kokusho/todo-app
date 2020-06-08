package de.webtech.user;

import de.webtech.entities.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {
    private static final transient Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user){
        User dbUser = new User();
        dbUser.setUsername(user.getUsername());
        dbUser.setPassword(user.getPassword());

        userRepository.save(dbUser);
        log.info("Successfully registered a new user: " + dbUser.getUsername());
        return dbUser;
    }

    @PostMapping("/login")
    public User loginUser(@Valid @RequestBody User user, @RequestParam(name = "rememberMe") Boolean rememberMe){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        token.setRememberMe(rememberMe.booleanValue());

        currentUser.login( token );
        //TODO add error handling
        log.info("Successfully logged in user: " + token.getUsername());
        return new User(token.getUsername(), new String(token.getPassword()));
    }

    @GetMapping("/logout")
    public boolean logoutUser(){
        Subject currentUser = SecurityUtils.getSubject();
        log.info("User logged out: " + currentUser.getPrincipal().toString());
        currentUser.logout();
        return true;
    }

    //FIXME for debug purposes only
    @GetMapping("/whoami")
    public User whoami(){
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setUsername((String) subject.getPrincipal());
        if(subject.isAuthenticated())
            return user;
        else
            return null;
    }
}
