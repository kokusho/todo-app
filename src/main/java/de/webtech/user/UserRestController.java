package de.webtech.user;

import de.webtech.entities.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {
    private static final transient Logger log = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public boolean registerUser(@RequestBody LoginInfo user){
        if(!user.isValid()){
            log.info("Cannot register user. User is invalid.");
            //TODO implement error handling also consider checking for uniqueness
            return false;
        }
        User dbUser = new User();
        dbUser.setUsername(user.getUsername());
        dbUser.setPassword(user.getPassword());
        try {
            userRepository.save(dbUser);
            log.info("Successfully registered a new user: " + dbUser.getUsername());
            return true;
        } catch(Exception e){
            log.error("Could not register the user: " + dbUser.getUsername(), e);
            return false;
        }
    }

    @PostMapping("/login")
    public boolean loginUser(@RequestBody LoginInfo user){
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        token.setRememberMe(true);

        try {
            currentUser.login( token );
            log.info("Successfully logged in user: " + token.getUsername());
            return true;
        } catch ( UnknownAccountException uae ) {
            //username wasn't in the system, show them an error message?
        } catch ( IncorrectCredentialsException ice ) {
            //password didn't match, try again?
        } catch ( LockedAccountException lae ) {
            //account for that username is locked - can't login.  Show them a message?
        } catch ( AuthenticationException ae ) {
            //unexpected condition - error?
        }
        return false;
    }

    @PostMapping("/logout")
    public boolean logoutUser(){
        Subject currentUser = SecurityUtils.getSubject();
        log.info("User logged out: " + currentUser.getPrincipal().toString());
        currentUser.logout();
        return true;
    }
}
