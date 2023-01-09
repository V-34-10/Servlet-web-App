package edu.fpm.pz.web.servlet;

import edu.fpm.pz.dao.EducationLevelDAO;
import edu.fpm.pz.dao.EmployeeDAO;
import edu.fpm.pz.dao.PositionDAO;
import edu.fpm.pz.model.EducationLevel;
import edu.fpm.pz.model.Employee;
import edu.fpm.pz.model.Position;
import edu.fpm.pz.validator.ValidatorEmployee;
import edu.fpm.pz.web.dto.EmployeeViewDTO;
import edu.fpm.pz.web.service.ToErrorPage;
import edu.fpm.pz.web.service.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class EmployeeController extends HttpServlet {
    protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    //private ObjectMapper mapper = new ObjectMapper();
    private final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
    private PositionDAO positionDAO = PositionDAO.getInstance();
    private EducationLevelDAO educationLevelDAO = EducationLevelDAO.getInstance();
    private final static String INSERT_OR_EDIT = "/Employee.jsp";
    private final static String LIST_EMPLOYEE = "/listEmployee.jsp";

    public void init() {
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_OK);
    }

    private void forwardToListEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EmployeeViewDTO> list = employeeDAO.getAll();
        request.setAttribute("employees", list);

        String forward = LIST_EMPLOYEE;
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void forwardToEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = INSERT_OR_EDIT;

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void prepareDataForEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id");
        String action = request.getParameter("action");
        int resultValidation = ValidatorEmployee.validateEmployeeIdFromRequest(strId);
        switch (resultValidation) {
            case HttpServletResponse.SC_BAD_REQUEST:
                ToErrorPage.forward(request, response, resultValidation, "Empty employeeId");
            case HttpServletResponse.SC_NOT_FOUND:
                ToErrorPage.forward(request, response, resultValidation, "Employee not found");
        }

        int id = Integer.parseInt(strId);
        Employee employee = employeeDAO.getById(id);
        List<Position> positionList = positionDAO.getAll();
        List<EducationLevel> educationLevelList = educationLevelDAO.getAll();

        request.setAttribute("educationLevels", educationLevelList);
        request.setAttribute("employee", employee);
        request.setAttribute("positions", positionList);
        request.setAttribute("action", action);
    }

    private void prepareDataForInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Position> positionList = positionDAO.getAll();
        List<EducationLevel> educationLevelList = educationLevelDAO.getAll();
        request.setAttribute("educationLevels", educationLevelList);
        request.setAttribute("positions", positionList);
        request.setAttribute("action", action);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null) {
            action = action.toLowerCase();
            switch (action) {
                case "delete":
                    doDelete(request, response);
                    break;
                case "insert":
                    prepareDataForInsert(request, response);
                    forwardToEmployee(request, response);
                    break;
                case "edit":
                    prepareDataForEdit(request, response);
                    forwardToEmployee(request, response);
                    break;
            }
            return;
        }
        forwardToListEmployee(request, response);
    }

    private Employee getDataForPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Employee employee = new Employee();
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("edit")) {
            employee.setEmployeeId(
                Util.getId(request.getParameter("employeeId"), "employee", request, response));
        }

        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setPositionId(Util.getId(request.getParameter("positionId"), "position", request, response));
        employee.setEducationLevelId(Util.getId(request.getParameter("educationLevelId"), "educationLevel", request, response));

        return employee;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Employee employee = getDataForPost(request, response);

        if (employee == null) return;

        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("edit")) {
            request.setAttribute("employee", employee);
            doPut(request, response);
            return;
        }

        try {
            employeeDAO.insert(employee);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Employee insert", e);
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        forwardToListEmployee(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id");
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();

        if (strId != null) {
            int id = Util.getId(strId, "employee", request, response);
            try {
                employeeDAO.delete(id);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Employee delete", e);
                ToErrorPage.forward(request, response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            }
        } else {
            new Exception("Unknown id").printStackTrace();
            ToErrorPage.forward(request, response, HttpServletResponse.SC_NOT_FOUND, "Unknown id for delete");
        }
        forwardToListEmployee(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Employee employee = null;
        try {
            employee = (Employee) request.getAttribute("employee");
        } catch (Exception e) {
            LOGGER.error("Employee: get attribute <employee> from request", e);
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        try {
            employeeDAO.update(employee);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Employee update", e);
            ToErrorPage.forward(request, response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        forwardToListEmployee(request, response);
    }
}
