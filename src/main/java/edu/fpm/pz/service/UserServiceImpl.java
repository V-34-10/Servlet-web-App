package edu.fpm.pz.service;

import edu.fpm.pz.dao.UserDAO;
import edu.fpm.pz.model.User;
import edu.fpm.pz.validator.ValidatorUser;
import edu.fpm.pz.web.dto.UserViewDTO;


public class UserServiceImpl implements UserService {


    private final SecurityService securityService = new SecurityServiceImpl();

    @Override
    public UserViewDTO login(String login, String password) {
        ValidatorUser.validateUserLogin(login);
        User user = UserDAO.getInstance().getByLogin(login);
        if (!securityService.isCorrectPassword(user, password)) {
            throw new IllegalArgumentException("Wrong password");
        }
        return user.asViewDTO();
    }

}
