package de.webtech.shiro;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtilsWrapper {

    public SecurityUtilsWrapper() {
    }

    public org.apache.shiro.subject.Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public String getPrincipal(){
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
}
