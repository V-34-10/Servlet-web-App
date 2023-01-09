package edu.fpm.pz.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fpm.pz.dao.EducationLevelDAO;
import edu.fpm.pz.model.EducationLevel;
import edu.fpm.pz.validator.ValidatorEmployee;
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


public class EducationLevelController extends HttpServlet {
    protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private EducationLevelDAO educationLevelDAO = EducationLevelDAO.getInstance();
    private final static String INSERT_OR_EDIT = "/EducationLevel.jsp";
    private final static String LIST_EMPLOYEE = "/listEducationLevel.jsp";

    public void init() {
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_OK);
    }

    private void forwardToListEducationLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EducationLevel> list = educationLevelDAO.getAll();
        request.setAttribute("educationLevels", list);

        String forward = LIST_EMPLOYEE;
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    private void forwardToEducationLevel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                ToErrorPage.forward(request, response, resultValidation, "Empty educationLevelId");
            case HttpServletResponse.SC_NOT_FOUND:
                ToErrorPage.forward(request, response, resultValidation, "EducationLevel not found");
        }

        int id = Integer.parseInt(strId);
        EducationLevel educationLevel = educationLevelDAO.getById(id);
        request.setAttribute("educationLevel", educationLevel);
        request.setAttribute("action", action);
    }

    private void prepareDataForInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
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
                    forwardToEducationLevel(request, response);
                    break;
                case "edit":
                    prepareDataForEdit(request, response);
                    forwardToEducationLevel(request, response);
                    break;
            }
            return;
        }
        forwardToListEducationLevel(request, response);
    }

    private EducationLevel getDataForPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EducationLevel educationLevel = new EducationLevel();
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("edit")) {
            educationLevel.setEducationLevelId(
                Util.getId(request.getParameter("educationLevelId"), "educationLevel", request, response));
        }
        educationLevel.setName(request.getParameter("name"));
        return educationLevel;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        EducationLevel educationLevel = getDataForPost(request, response);

        if (educationLevel == null) return;

        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("edit")) {
            request.setAttribute("educationLevel", educationLevel);
            doPut(request, response);
            return;
        }

        try {
            educationLevelDAO.insert(educationLevel);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Employee insert", e);
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        forwardToListEducationLevel(request, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id");
        EducationLevelDAO employeeDAO = EducationLevelDAO.getInstance();

        if (strId != null) {
            int id = Util.getId(strId, "educationLevel", request, response);
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
        forwardToListEducationLevel(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        EducationLevel educationLevel = null;
        try {
            educationLevel = (EducationLevel) request.getAttribute("educationLevel");
        } catch (Exception e) {
            LOGGER.error("Employee: get attribute <employee> from request", e);
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        try {
            educationLevelDAO.update(educationLevel);
        } catch (IllegalArgumentException e) {
            LOGGER.error("EducationLevel update", e);
            ToErrorPage.forward(request, response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        forwardToListEducationLevel(request, response);
    }
}
