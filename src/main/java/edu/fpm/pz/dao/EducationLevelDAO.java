package edu.fpm.pz.dao;

import edu.fpm.pz.model.EducationLevel;
import edu.fpm.pz.service.ServiceDB;
import edu.fpm.pz.validator.ValidatorEmployee;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EducationLevelDAO implements iDAO<EducationLevel, EducationLevel> {
    private EducationLevelDAO() {}

    private static EducationLevelDAO instance;

    public static EducationLevelDAO getInstance() {
        if (instance == null) instance = new EducationLevelDAO();
        return instance;
    }

    /**
     * insert one record into table
     */
    @Override
    public void insert(EducationLevel entity) {
        ValidatorEmployee.validateEducationLevel(entity);
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(
                     "INSERT INTO education_levels (name) VALUES (?)")) {
            stat.setString(1, entity.getName());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * select * from Entity
     */
    @Override
    public List<EducationLevel> getAll() {
        List<EducationLevel> list = new ArrayList<>();
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {
            try (ResultSet result = stat.executeQuery("SELECT * FROM education_levels ORDER BY name")) {
                while (result.next()) {
                    int educationLevelId = result.getInt("education_level_id");
                    String name = result.getString("name");
                    list.add(new EducationLevel(educationLevelId, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * get entity by id
     *
     * @param id record id
     * @return
     */
    @Override
    public EducationLevel getById(int id) {
        EducationLevel element = null;
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(
                     "SELECT * FROM education_levels WHERE education_level_id = ?")) {
            stat.setInt(1, id);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                String name = result.getString("name");
                element = new EducationLevel(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void update(EducationLevel entity) {
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(
                     "UPDATE education_levels SET name = ? WHERE education_level_id = ?")) {
            stat.setString(1, entity.getName());
            stat.setInt(2, entity.getEducationLevelId());
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all from Entity
     */
    @Override
    public void deleteAll() {
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {
            stat.executeUpdate("DELETE FROM education_levels");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * update delete where entity_id = id
     *
     * @param id entity_id
     */
    @Override
    public void delete(int id) {
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement("DELETE FROM education_levels where education_level_id = ?")) {
            stat.setInt(1, id);
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get List of Ids
     *
     * @return List of Ids
     */
    @Override
    public List<Integer> getListOfId() {
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {

            try (ResultSet result = stat.executeQuery("SELECT education_level_id FROM education_levels")) {
                List<Integer> resultList = new ArrayList<>();
                while (result.next()) resultList.add(result.getInt("education_level_id"));
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
