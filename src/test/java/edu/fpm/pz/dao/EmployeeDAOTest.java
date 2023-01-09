package edu.fpm.pz.dao;

import edu.fpm.pz.model.Employee;
import edu.fpm.pz.web.dto.EmployeeViewDTO;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.OptionalInt;

public class EmployeeDAOTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private EmployeeDAO dao = EmployeeDAO.getInstance();

  private int getCountRecord() {
    List<Integer> list = dao.getListOfId();
    return list.size();
  }

  private int maxId() {
    List<Integer> list = dao.getListOfId();
    OptionalInt max = list.stream().mapToInt(Integer::intValue).max();
    return max.getAsInt();
  }

  @Test
  public void getInstance() {
    //WHEN
    EmployeeDAO instance = dao;
    //THEN
    Assert.assertNotNull(instance);
  }

  @Test
  public void insertPositive() {
    //GIVEN
    int sizeBeforeInsert = getCountRecord();
    int expectedSize = sizeBeforeInsert + 1;
    //WHEN
    List<Integer> list = PositionDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    dao.insert(new Employee(0, "firstName", "lastName", max, "",
        1, "Базова"));
    int resultSize = getCountRecord();
    int maxIndex = maxId();
    dao.delete(maxIndex);
    //THEN
    Assert.assertEquals(expectedSize, resultSize);
  }

  @Test
  public void insertDuplicate() {
    //GIVEN
    int sizeBeforeInsert = getCountRecord();
    int expectedSize = sizeBeforeInsert + 1;
    int maxIndex = maxId();
    Employee e = dao.getById(maxIndex);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("duplicate employee");
    //WHEN
    dao.insert(e);
    int resultSize = getCountRecord();
    thrown = ExpectedException.none();
    //THEN
    Assert.assertEquals(expectedSize, resultSize);
  }


  @Test
  public void getAll() {
    //GIVEN
    int expectedSize = getCountRecord();
    //WHEN
    List<EmployeeViewDTO> list = dao.getAll();
    //THEN
    Assert.assertEquals(expectedSize, list.size());
  }

  @Test
  public void getById() {
    //GIVEN
    List<Integer> list = PositionDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Employee e = new Employee(0, "firstName", "lastName", max, "Бухгалтер",
        1, "Базова");
    dao.insert(e);
    int maxIndex = maxId();
    e.setEmployeeId(maxIndex);
    //WHEN
    Employee result = dao.getById(maxIndex);
    dao.delete(maxIndex);
    //THEN
    Assert.assertNotNull(result);
    Assert.assertEquals(e, result);
  }

  @Test
  public void update() {
    //GIVEN
    List<Integer> list = PositionDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Employee e = new Employee(0, "firstName", "lastName", max, "Бухгалтер",
        1, "Базова");
    dao.insert(e);
    int maxIndex = maxId();
    e.setFirstName("newFirstName");
    e.setEmployeeId(maxIndex);
    //WHEN
    dao.update(e);
    Employee result = dao.getById(maxIndex);
    dao.delete(maxIndex);
    //THEN
    Assert.assertEquals(e, result);
  }

  @Test
  public void delete() {
    //GIVEN
    List<Integer> list = PositionDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Employee e = new Employee(0, "firstName", "lastName", max, "",
        1, "Базова");
    dao.insert(e);
    int sizeBeforeDelete = getCountRecord();
    int expectedSize = sizeBeforeDelete - 1;

    int maxIndex = maxId();
    System.out.println("max index " + maxIndex);
    //WHEN
    dao.delete(maxIndex);
    Employee result = dao.getById(maxIndex);
    //THEN
    Assert.assertNull(result);
    Assert.assertEquals(expectedSize, getCountRecord());
  }
}