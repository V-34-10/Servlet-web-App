package edu.fpm.pz.service;


import edu.fpm.pz.model.User;

public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean isCorrectPassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
