package de.webtech.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
public class User {

    @Id
    @Column(length = 30)
    @NotNull(message = "Username must be present.")
    @Size(min = 4, max = 30, message = "Username must be longer than 4 characters and shorter than 30 characters.")
    private String username;

    @Column(length = 60)
    @NotNull(message = "Password must be present.")
    @Size(min = 6, max = 60, message = "Password must be longer than 6 characters and shorter than 60 characters.")
    @JsonProperty(access = WRITE_ONLY) //property will be deserialized but not serialized
    private String password;

    @JsonIgnore
    @ElementCollection
    private Set<Permission> permissions;

    @JsonIgnore
    @ElementCollection
    private Set<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username){
        this.username = username;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return this.username.equals(((User) obj).getUsername());
        }
        return false;
    }
}
