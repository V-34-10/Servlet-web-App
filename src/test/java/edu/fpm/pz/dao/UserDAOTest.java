package edu.fpm.pz.dao;

import edu.fpm.pz.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.OptionalInt;

public class UserDAOTest {

  private UserDAO dao = UserDAO.getInstance();

  private int maxId() {
    List<Integer> list = dao.getListOfId();
    OptionalInt max = list.stream().mapToInt(Integer::intValue).max();
    return max.getAsInt();
  }

  @Test
  public void getInstance() {
    //WHEN
    UserDAO instance = dao;
    //THEN
    Assert.assertNotNull(instance);
  }

  @Test
  public void getByLogin() {
    //GIVEN
    List<Integer> list = PermissionDAO.getInstance().getListOfId();
    int maxIndex = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    User user = new User(0, "test", "test", maxIndex, "");
    dao.create(user);
    //WHEN
    User result = dao.getByLogin("test");
    dao.delete(maxId());
    //THEN
    Assert.assertNotNull(result);
  }

  @Test
  public void create() {
    //GIVEN
    List<Integer> list = PermissionDAO.getInstance().getListOfId();
    int maxIndex = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    User user = new User(0, "test", "test", maxIndex, "");
    dao.create(user);
    //WHEN
    User result = dao.getByLogin("test");
    dao.delete(maxId());
    //THEN
    Assert.assertNotNull(result);
  }
}