package edu.fpm.pz.dao;

import edu.fpm.pz.model.Position;
import edu.fpm.pz.service.ServiceDB;
import edu.fpm.pz.validator.ValidatorEmployee;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements iDAO<Position, Position> {
    private PositionDAO() {}

    private static PositionDAO instance;

    public static PositionDAO getInstance() {
        if (instance == null) instance = new PositionDAO();
        return instance;
    }

    /**
     * insert one record into table
     */
    @Override
    public void insert(Position entity) {
        ValidatorEmployee.validatePosition(entity);
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(
                     "INSERT INTO positions (name) VALUES (?)")) {
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
    public List<Position> getAll() {
        List<Position> list = new ArrayList<>();
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {
            try (ResultSet result = stat.executeQuery("SELECT * FROM positions ORDER BY name")) {
                while (result.next()) {
                    int positionId = result.getInt("position_id");
                    String name = result.getString("name");
                    list.add(new Position(positionId, name));
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
    public Position getById(int id) {
        Position element = null;
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(
                     "SELECT * FROM positions WHERE position_id = ?")) {
            stat.setInt(1, id);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                String name = result.getString("name");
                element = new Position(id, name);
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
    public void update(Position entity) {
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(
                     "UPDATE positions SET name = ? WHERE position_id = ?")) {
            stat.setString(1, entity.getName());
            stat.setInt(2, entity.getPositionId());
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
            stat.executeUpdate("DELETE FROM positions");
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
             PreparedStatement stat = conn.prepareStatement("DELETE FROM positions where position_id = ?")) {
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

            try (ResultSet result = stat.executeQuery("SELECT position_id FROM positions")) {
                List<Integer> resultList = new ArrayList<>();
                while (result.next()) resultList.add(result.getInt("position_id"));
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
