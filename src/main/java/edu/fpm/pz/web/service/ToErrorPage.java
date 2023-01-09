package edu.fpm.pz.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToErrorPage {
    protected static Logger LOGGER = LoggerFactory.getLogger(ToErrorPage.class);

    public static void forward(HttpServletRequest request, HttpServletResponse response, int ErrorCode, String message) throws ServletException, IOException {
        final String ERROR_PAGE = "/ErrorPage.jsp";
        request.setAttribute("ErrorCode", message);
        RequestDispatcher view = request.getRequestDispatcher(ERROR_PAGE);
        LOGGER.info("Forwarded to error page");
        view.forward(request, response);
    }
}
