package edu.fpm.pz.dao;

import edu.fpm.pz.model.Permission;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.OptionalInt;

public class PermissionDAOTest {

  private PermissionDAO dao = PermissionDAO.getInstance();

  private int maxId() {
    List<Integer> list = dao.getListOfId();
    OptionalInt max = list.stream().mapToInt(Integer::intValue).max();
    return max.getAsInt();
  }

  @Test
  public void getInstance() {
    //WHEN
    PermissionDAO instance = dao;
    //THEN
    Assert.assertNotNull(instance);
  }

  @Test
  public void getById() {
    //GIVEN
    int maxIndex = maxId();
    //WHEN
    Permission result = dao.getById(maxIndex);
    //THEN
    Assert.assertNotNull(result);
  }

}