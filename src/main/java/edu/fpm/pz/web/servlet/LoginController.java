package edu.fpm.pz.web.servlet;

import edu.fpm.pz.service.UserService;
import edu.fpm.pz.service.UserServiceImpl;
import edu.fpm.pz.web.dto.UserViewDTO;
import edu.fpm.pz.web.service.ToErrorPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "loginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    protected static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/");
        HttpSession session = req.getSession(true);
        session.removeAttribute("user");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserViewDTO user = null;
        try {
            user = userService.login(login, password);
        } catch (IllegalArgumentException e){
            LOGGER.error("User login", e);
            ToErrorPage.forward(req, resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);
        resp.sendRedirect("/profile");
    }
}
