package edu.fpm.pz.web.servlet;

import edu.fpm.pz.dao.PositionDAO;
import edu.fpm.pz.model.Position;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class PositionController extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();
    private PositionDAO positionDAO = PositionDAO.getInstance();

    public void init() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_OK);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        String strId = request.getParameter("id");

        if (strId != null) {
            int id;
            try {
                id = Integer.parseInt(strId);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Position position = positionDAO.getById(id);
            if (position == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            mapper.writeValue(response.getWriter(), position);
        } else {
            List<Position> list = positionDAO.getAll();
            mapper.writeValue(response.getWriter(), list);
        }
    }

    /**
     * insert array of Position
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request - array of Position to insert into Position
        request.setCharacterEncoding("UTF-8");
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //String strArr = request.getParameter("arr");
        Position[] positionArr;
        //Position position;
        //if (strArr.equals("1")) {
        try {
            positionArr = mapper.readValue(request.getReader(), Position[].class);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        for (Position position: positionArr) {
            try {
            positionDAO.insert(position);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

       /* } else {
            try {
                position = mapper.readValue(request.getReader(), Position.class);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            try {
                positionDAO.insert(position);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }*/
        response.sendError(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id");

        if (strId != null) {
            int id;
            try {
                id = Integer.parseInt(strId);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            try {
                positionDAO.delete(id);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } else {
            //delete all records - not supported
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Position position;
        try {
            position = mapper.readValue(request.getReader(), Position.class);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            positionDAO.update(position);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

    }
}
