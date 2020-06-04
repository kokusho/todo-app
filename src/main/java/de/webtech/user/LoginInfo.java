package de.webtech.user;

public class LoginInfo {
    private String username;    //principal
    private String password;    //credential

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

    public boolean isValid() {
        return this.username != null && !this.username.trim().equals("") && this.password != null && this.password.length() > 6;
    }
}
