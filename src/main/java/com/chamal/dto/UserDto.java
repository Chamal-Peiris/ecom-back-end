package com.chamal.dto;

import com.chamal.constant.UserRole;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class UserDto implements Serializable {
    private String username;
    private String password;
    private List<UserRole> userRole;
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

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }
}
