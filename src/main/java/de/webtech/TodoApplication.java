package de.webtech;

import de.webtech.shiro.UserRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    @Bean
    public Realm realm() {
        return new UserRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        //FIRST MATCH WINS policy
        //chainDefinition.addPathDefinition( "/login", "anon");
        //chainDefinition.addPathDefinition( "/register", "anon");

        // all other paths require a logged in user
        //chainDefinition.addPathDefinition("/*.js", "anon");
        //chainDefinition.addPathDefinition("/*.css", "anon");
        //chainDefinition.addPathDefinition("/*.ico", "anon");
        //chainDefinition.addPathDefinition("/**", "authc");

        chainDefinition.addPathDefinition("/dashboard**", "authc");
        chainDefinition.addPathDefinition("/todo**", "authc");
        return chainDefinition;
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}
