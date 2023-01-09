package edu.fpm.pz.model;

import java.io.Serializable;
import java.util.Objects;


public class Permission implements Serializable {
    private int permission_id;
    private String permissionName;

    public Permission(int permission_id, String permissionMame) {
        this.permission_id = permission_id;
        this.permissionName = permissionMame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;
        Permission that = (Permission) o;
        return Objects.equals(permissionName, that.permissionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionName);
    }
}
