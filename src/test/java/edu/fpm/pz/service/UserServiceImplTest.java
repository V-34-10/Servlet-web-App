package edu.fpm.pz.service;

import edu.fpm.pz.dao.PermissionDAO;
import edu.fpm.pz.dao.UserDAO;
import edu.fpm.pz.model.User;
import edu.fpm.pz.web.dto.UserViewDTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserServiceImplTest {

  private UserDAO dao = UserDAO.getInstance();

  @Test
  public void login() {
    //GIVEN
    List<Integer> list = PermissionDAO.getInstance().getListOfId();
    int maxIndex = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    User user = new User(0, "test", "test", maxIndex, "");
    dao.create(user);
    //WHEN
    UserViewDTO result = new UserServiceImpl().login("test", "test");
    list = dao.getListOfId();
    maxIndex = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    dao.delete(maxIndex);
    //THEN
    Assert.assertEquals(user.asViewDTO(), result);
  }
}