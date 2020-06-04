package de.webtech.shiro;

import de.webtech.entities.User;
import de.webtech.user.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserRealm extends AuthorizingRealm {
    private static final transient Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        log.debug("Querying authorization info");
        //TODO impelment;
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("Authenticating a user");
        if(token.getUsername() == null || token.getUsername().isEmpty()){
            log.warn("Tried to authenticate a user, but no username was given.");
            throw new UnknownAccountException();
        }
        Optional<User> userOpt = userRepository.findById(token.getUsername());
        //TODO check password too, i guess?
        if(userOpt.isEmpty()){
            log.warn("Tried to authenticate a user, but user was not found.");
            throw new UnknownAccountException();
        }
        log.info("Successfully authenticated user: ", token.getUsername());
        return new SimpleAuthenticationInfo(token.getUsername(), userOpt.get().getPassword(), getName());
    }
}
