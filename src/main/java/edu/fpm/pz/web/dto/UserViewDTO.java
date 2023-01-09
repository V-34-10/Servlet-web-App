package edu.fpm.pz.web.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserViewDTO implements Serializable {
    private int userId;
    private String login;
    private String permissionName;

    public UserViewDTO() {}

    public UserViewDTO(int userId, String login, String permissionName) {
        this.userId = userId;
        this.login = login;
        this.permissionName = permissionName;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
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

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserViewDTO)) return false;
        UserViewDTO that = (UserViewDTO) o;
        return Objects.equals(login, that.login);
    }


}
