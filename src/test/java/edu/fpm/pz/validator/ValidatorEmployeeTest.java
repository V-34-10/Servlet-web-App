package edu.fpm.pz.validator;

import edu.fpm.pz.dao.EmployeeDAO;
import edu.fpm.pz.dao.PositionDAO;
import edu.fpm.pz.model.Employee;
import edu.fpm.pz.model.Position;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ValidatorEmployeeTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void validatePositionId() {
    //GIVEN
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("not valid position id");
    //WHEN
    ValidatorEmployee.validatePositionId(0);
    thrown = ExpectedException.none();
  }

  @Test
  public void validatePositionNameEmpty() {
    //GIVEN
    List<Integer> list = PositionDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Position p = PositionDAO.getInstance().getById(max);
    p.setName("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("position name is empty");
    //WHEN
    ValidatorEmployee.validatePosition(p);
    thrown = ExpectedException.none();
  }

  @Test
  public void validatePositionDuplicate() {
    //GIVEN
    List<Integer> list = PositionDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Position p = PositionDAO.getInstance().getById(max);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("duplicate position");
    //WHEN
    ValidatorEmployee.validatePosition(p);
    thrown = ExpectedException.none();
  }

  @Test
  public void validateEmployeeId() {
    //GIVEN
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("not valid employee id");
    //WHEN
    ValidatorEmployee.validateEmployeeId(0);
    thrown = ExpectedException.none();
  }

  @Test
  public void validateEmployeeDuplicate() {
    //GIVEN
    List<Integer> list = EmployeeDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Employee e = EmployeeDAO.getInstance().getById(max);
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("duplicate employee");
    //WHEN
    ValidatorEmployee.validateEmployee(e);
    thrown = ExpectedException.none();
  }

  @Test
  public void validateEmployeeFirstNameEmpty() {
    //GIVEN
    List<Integer> list = EmployeeDAO.getInstance().getListOfId();
    int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
    Employee e = EmployeeDAO.getInstance().getById(max);
    e.setFirstName("");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("employee firstName is empty");
    //WHEN
    ValidatorEmployee.validateEmployee(e);
    thrown = ExpectedException.none();
  }

  @Test
  public void validateEmployeeIdFromRequest() {
    //GIVEN
    int expectedResponse = HttpServletResponse.SC_BAD_REQUEST;
    //WHEN
    int result = ValidatorEmployee.validateEmployeeIdFromRequest("");
    //THEN
    Assert.assertEquals(expectedResponse, result);
  }
}