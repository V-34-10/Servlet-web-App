package edu.fpm.pz.validator;

import edu.fpm.pz.dao.EducationLevelDAO;
import edu.fpm.pz.dao.EmployeeDAO;
import edu.fpm.pz.dao.PositionDAO;
import edu.fpm.pz.model.EducationLevel;
import edu.fpm.pz.model.Employee;
import edu.fpm.pz.model.Position;
import edu.fpm.pz.web.dto.EmployeeViewDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ValidatorEmployee {
    public static void validatePositionId(int id) {
        Position position = PositionDAO.getInstance().getById(id);
        if (position == null) throw new IllegalArgumentException("not valid position id");
    }

    public static void validatePosition(Position position) {
        if (position.getName() == null) throw new IllegalArgumentException("position name is null");
        if (position.getName().isEmpty()) throw new IllegalArgumentException("position name is empty");
        List<Position> list = PositionDAO.getInstance().getAll();
        if (list.contains(position)) throw new IllegalArgumentException("duplicate position");
    }

    public static void validateEducationLevel(EducationLevel educationLevel) {
        if (educationLevel.getName() == null) throw new IllegalArgumentException("position name is null");
        if (educationLevel.getName().isEmpty()) throw new IllegalArgumentException("position name is empty");
        List<EducationLevel> list = EducationLevelDAO.getInstance().getAll();
        if (list.contains(educationLevel)) throw new IllegalArgumentException("duplicate position");
    }

    public static void validateEmployeeId(int id) {
        Employee employee = EmployeeDAO.getInstance().getById(id);
        if (employee == null) throw new IllegalArgumentException("not valid employee id");
    }

    public static void validateEmployee(Employee employee) {
        if (employee.getFirstName() == null) throw new IllegalArgumentException("employee firstName is null");
        if (employee.getFirstName().isEmpty()) throw new IllegalArgumentException("employee firstName is empty");
        if (employee.getLastName() == null) throw new IllegalArgumentException("employee lastName is null");
        if (employee.getLastName().isEmpty()) throw new IllegalArgumentException("employee lastName is empty");
        if (employee.getPositionId() == 0) throw new IllegalArgumentException("employee position is null");
        List<EmployeeViewDTO> list = EmployeeDAO.getInstance().getAll();
        if (list.contains(employee.asViewDTO())) throw new IllegalArgumentException("duplicate employee");
    }

    public static int validateEmployeeIdFromRequest(String strId) {
        int id;
        try {
            id = Integer.parseInt(strId);
        } catch (NumberFormatException e) {
            return HttpServletResponse.SC_BAD_REQUEST;
        }
        Employee employee = EmployeeDAO.getInstance().getById(id);
        if (employee == null) {
            return HttpServletResponse.SC_NOT_FOUND;
        }
        return HttpServletResponse.SC_OK;
    }

}
