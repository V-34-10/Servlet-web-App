package edu.fpm.pz.web.service;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Util {
    public static int getId(String value, String errorEntity,
                            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST,
                    "Empty " + errorEntity + " id");
        }
        return id;
    }

}
