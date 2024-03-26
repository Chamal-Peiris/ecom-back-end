package com.chamal.dto;

import com.chamal.constant.UserRole;
import com.chamal.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class UserDto implements Serializable {
    private String username;
    private String password;

    private Set<UserRole> userRole;
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

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public UserDto() {
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userRole = user.getRole();
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }


}
