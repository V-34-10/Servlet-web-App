package edu.fpm.pz.model;

import edu.fpm.pz.web.dto.UserViewDTO;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private int userId;
    private String login;
    private String password;
    private int permissionId;
    private String permissionName;

    public User() {}

    public User(int userId, String login, String password, int permissionId, String permissionName) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public UserViewDTO asViewDTO() {
        return new UserViewDTO(userId, login, permissionName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }


}
