package edu.fpm.pz.service;

import edu.fpm.pz.web.dto.UserViewDTO;

public interface UserService {
    UserViewDTO login(String login, String password);
}