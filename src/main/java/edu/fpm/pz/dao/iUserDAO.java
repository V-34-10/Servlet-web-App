package edu.fpm.pz.dao;

import edu.fpm.pz.model.User;

import java.util.List;

public interface iUserDAO {
    User getByLogin(String login);
    User create(User user);

    void delete(int id);
    public List<Integer> getListOfId();
}
