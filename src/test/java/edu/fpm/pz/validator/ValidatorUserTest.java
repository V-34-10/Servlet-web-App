package edu.fpm.pz.validator;

import edu.fpm.pz.dao.PermissionDAO;
import edu.fpm.pz.dao.UserDAO;
import edu.fpm.pz.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

public class ValidatorUserTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void validateUser() {
    //GIVEN
    List<Integer> list = PermissionDAO.getInstance().getListOfId();
    int maxIndex = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    User user = new User(0, "test", "test", maxIndex, "");
    UserDAO.getInstance().create(user);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("duplicate user");
    //WHEN
    try {
      ValidatorUser.validateUser(user);
    } finally {
      list = UserDAO.getInstance().getListOfId();
      maxIndex = list.stream().mapToInt(Integer::intValue).max().getAsInt();
      UserDAO.getInstance().delete(maxIndex);
      thrown = ExpectedException.none();
    }

  }

  @Test
  public void validateUserLogin() {
    //GIVEN
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("User with login <test> not registered");
    //WHEN
    ValidatorUser.validateUserLogin("test");
    thrown = ExpectedException.none();
  }

  @Test
  public void validatePermissionId() {
    //GIVEN
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("not valid permission id");
    //WHEN
    ValidatorUser.validatePermissionId(0);
    thrown = ExpectedException.none();
  }

  @Test
  public void validateUserId() {
    //GIVEN
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("not valid user id");
    //WHEN
    ValidatorUser.validateUserId(0);
    thrown = ExpectedException.none();
  }
}