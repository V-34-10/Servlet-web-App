package edu.fpm.pz.dao;

import edu.fpm.pz.model.User;
import edu.fpm.pz.service.ServiceDB;
import edu.fpm.pz.validator.ValidatorUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements iUserDAO {
    protected static Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    private UserDAO() {}

    private static UserDAO instance;

    public static UserDAO getInstance() {
        if (instance == null) instance = new UserDAO();
        return instance;
    }

    private final String GET_BY_LOGIN_QUERY = "SELECT * FROM users, permissions " +
            "WHERE login = ? AND users.permission_id = permissions.permission_id";
    @Override
    public User getByLogin(String login) {
        User element = null;
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(GET_BY_LOGIN_QUERY)) {
            stat.setString(1, login);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                int userId = result.getInt("user_id");
                String password = result.getString("password");
                int permissionId = result.getInt("permission_id");
                String permissionName = result.getString("name");
                element = new User(userId, login, password, permissionId, permissionName);
                LOGGER.info("Returned user by login={}", login);
            } else {
                LOGGER.info("User by login={} not found", login);
            }
        } catch (SQLException e) {
            LOGGER.error("User by login={}", login, e);
        } catch (IOException e) {
            LOGGER.error("User by login={}", login, e);
        }
        return element;
    }

    private final String INSERT_QUERY = "INSERT INTO users " +
            "(login, password, permission_id) " +
            "VALUES (?, ?, ?)";
    @Override
    public User create(User user) {
        ValidatorUser.validateUser(user);
        ValidatorUser.validatePermissionId(user.getPermissionId());

        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(INSERT_QUERY)) {

            stat.setString(1, user.getLogin());
            stat.setString(2, user.getPassword());
            stat.setInt(3, user.getPermissionId());
            stat.executeUpdate();
            LOGGER.info("User {} created", user.getLogin());
        } catch (SQLException e) {
            LOGGER.error("User {} not created", user.getLogin(), e);
        } catch (IOException e) {
            LOGGER.error("User {} not created", user.getLogin(), e);
        }
        return null;
    }

    private final String DELETE_BY_ID_QUERY = "DELETE FROM users where user_id = ?";
    /**
     * delete where entity_id = id
     *
     * @param id entity_id
     */
    @Override
    public void delete(int id) {
        ValidatorUser.validateUserId(id);
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(DELETE_BY_ID_QUERY)) {
            stat.setInt(1, id);
            stat.executeUpdate();
            LOGGER.info("User id={} deleted", id);
        } catch (SQLException e) {
            LOGGER.error("User delete id={}", id, e);
        } catch (IOException e) {
            LOGGER.error("User delete id={}", id, e);
        }
    }

    private final String SELECT_ID_QUERY = "SELECT user_id FROM users";
    /**
     * get List of Ids
     *
     * @return List of Ids
     */
    @Override
    public List<Integer> getListOfId() {
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {

            try (ResultSet result = stat.executeQuery(SELECT_ID_QUERY)) {
                List<Integer> resultList = new ArrayList<>();
                while (result.next()) resultList.add(result.getInt("user_id"));
                return resultList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
