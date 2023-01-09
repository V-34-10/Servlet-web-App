package edu.fpm.pz.service;


import edu.fpm.pz.model.User;

public interface SecurityService {

    boolean isCorrectPassword(User user, String password);
}
