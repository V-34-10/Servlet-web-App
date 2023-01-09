package edu.fpm.pz.dao;


import edu.fpm.pz.model.Permission;
import edu.fpm.pz.service.ServiceDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO implements iDAO<Permission, Permission> {
    protected static Logger LOGGER = LoggerFactory.getLogger(PermissionDAO.class);

    private PermissionDAO() {
    }

    private static PermissionDAO instance;

    public static PermissionDAO getInstance() {
        if (instance == null) instance = new PermissionDAO();
        return instance;
    }

    /**
     * insert list of Entities
     *
     * @param entity
     */
    @Override
    public void insert(Permission entity) {

    }

    /**
     * get all records from Entity
     */
    @Override
    public List<Permission> getAll() {

        return null;
    }


    private final String SELECT_BY_ID_QUERY = "SELECT * FROM permissions WHERE permission_id = ?";
    /**
     * get entity by id
     *
     * @param id record id
     * @return
     */
    @Override
    public Permission getById(int id) {
        Permission element = null;
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            stat.setInt(1, id);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                int permission_id = result.getInt("permission_id");
                String name = result.getString("name");
                element = new Permission(permission_id, name);
                LOGGER.info("Returned permission by id={}", id);
            } else {
                LOGGER.info("Permission by id={} not found", id);
            }
        } catch (SQLException e) {
            LOGGER.error("Permission by id={}", id, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return element;
    }

    /**
     * update record
     *
     * @param entity entity
     */
    @Override
    public void update(Permission entity) {

    }

    /**
     * delete all from Entity
     */
    @Override
    public void deleteAll() {

    }

    /**
     * delete where entity_id = id
     *
     * @param id entity_id
     */
    @Override
    public void delete(int id) {

    }

    private final String SELECT_PERMISSION_ID_QUERY = "SELECT permission_id FROM permissions";
    /**
     * get List of Ids
     *
     * @return List of Ids
     */
    @Override
    public List<Integer> getListOfId() {
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {

            try (ResultSet result = stat.executeQuery(SELECT_PERMISSION_ID_QUERY)) {
                List<Integer> resultList = new ArrayList<>();
                while (result.next()) resultList.add(result.getInt("permission_id"));
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
