package edu.fpm.pz.validator;

import edu.fpm.pz.dao.PermissionDAO;
import edu.fpm.pz.dao.UserDAO;
import edu.fpm.pz.model.Permission;
import edu.fpm.pz.model.User;

import java.util.List;

public class ValidatorUser {
    public static void validateUser(User user) {
        if (user.getLogin() == null) throw new IllegalArgumentException("user login is null");
        if (user.getLogin().isEmpty()) throw new IllegalArgumentException("user login is empty");
        if (user.getPassword() == null) throw new IllegalArgumentException("user password is null");
        if (user.getPassword().isEmpty()) throw new IllegalArgumentException("user password is empty");
        if (user.getPermissionId() == 0) throw new IllegalArgumentException("user permission is null");
        User userInDB = UserDAO.getInstance().getByLogin(user.getLogin());
        if (userInDB != null)
            throw new IllegalArgumentException("duplicate user");
    }

    public static void validateUserLogin(String login) {
        User user = UserDAO.getInstance().getByLogin(login);
        if (user == null)
            throw new IllegalArgumentException("User with login <" + login + "> not registered");
    }

    public static void validatePermissionId(int id) {
        Permission permission = PermissionDAO.getInstance().getById(id);
        if (permission == null) throw new IllegalArgumentException("not valid permission id");
    }

    public static void validateUserId(int id) {
        List<Integer> list = UserDAO.getInstance().getListOfId();
        if (!list.contains(id)) throw new IllegalArgumentException("not valid user id");
    }

}
