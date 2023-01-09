package edu.fpm.pz.dao;

import edu.fpm.pz.model.Employee;
import edu.fpm.pz.service.ServiceDB;
import edu.fpm.pz.validator.ValidatorEmployee;
import edu.fpm.pz.web.dto.EmployeeViewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements iDAO<Employee, EmployeeViewDTO> {
    protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeDAO.class);
    private EmployeeDAO() {
    }

    private static EmployeeDAO instance;

    public static EmployeeDAO getInstance() {
        if (instance == null) instance = new EmployeeDAO();
        return instance;
    }


    private final String INSERT_QUERY = "INSERT INTO employees " +
            "(firstName, lastName, position_id, education_level_id) " +
            "VALUES (?, ?, ?, ?)";
    /**
     * insert list of Entities
     *
     * @param entity
     */
    @Override
    public void insert(Employee entity) {

        ValidatorEmployee.validateEmployee(entity);
        ValidatorEmployee.validatePositionId(entity.getPositionId());

        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(INSERT_QUERY)) {

            stat.setString(1, entity.getFirstName());
            stat.setString(2, entity.getLastName());
            stat.setInt(3, entity.getPositionId());
            stat.setInt(4, entity.getEducationLevelId());

            stat.executeUpdate();
            LOGGER.info("Employee inserted");
        } catch (SQLException e) {
            LOGGER.error("Employee not inserted", e);
        } catch (IOException e) {
            LOGGER.error("Employee not inserted", e);
        }
    }

    private final String SELECT_QUERY = "SELECT * FROM employees, positions, education_levels " +
            "WHERE employees.position_id = positions.position_id " +
            "&& employees.education_level_id = education_levels.education_level_id " +
            "ORDER BY lastName";
    /**
     * get all records from Entity
     */
    @Override
    public List<EmployeeViewDTO> getAll() {
        List<EmployeeViewDTO> list = new ArrayList<>();
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {
            try (ResultSet result = stat.executeQuery(SELECT_QUERY)) {
                while (result.next()) {
                    int employeeId = result.getInt("employee_id");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    int positionId = result.getInt("position_id");
                    String positionName = result.getString("positions.name");
                    int educationLevelId = result.getInt("education_level_id");
                    String educationLevelName = result.getString("education_levels.name");
                    EmployeeViewDTO employee =
                            new EmployeeViewDTO(employeeId, firstName, lastName, positionId, positionName,
                                    educationLevelId, educationLevelName);
                    list.add(employee);
                }
                LOGGER.info("Returned List of employees");
            }
        } catch (SQLException e) {
            LOGGER.error("List of employees", e);
        } catch (IOException e) {
            LOGGER.error("List of employees", e);
        }
        return list;
    }

    private final String SELECT_BY_ID_QUERY = "SELECT * FROM employees, positions, education_levels " +
            "WHERE employees.position_id = positions.position_id AND employee_id = ?";
    /**
     * get entity by id
     *
     * @param id record id
     * @return Employee or null
     */
    @Override
    public Employee getById(int id) {
        Employee element = null;
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            stat.setInt(1, id);
            ResultSet result = stat.executeQuery();
            if (result.next()) {
                int positionId = result.getInt("position_id");
                int educationLevelId = result.getInt("education_level_id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String positionName = result.getString("positions.name");
                String educationLevelName = result.getString("education_levels.name");

                element = new Employee(id, firstName, lastName, positionId, positionName, educationLevelId, educationLevelName);
                LOGGER.info("Returned employee by id={}", id);
            } else {
                LOGGER.info("Employee by id={} not found", id);
            }
        } catch (SQLException e) {
            LOGGER.error("Employee by id={}", id, e);
        } catch (IOException e) {
            LOGGER.error("Employee by id={}", id, e);
        }
        return element;
    }

    private final String UPDATE_QUERY = "UPDATE employees " +
            "SET firstName = ?, lastName = ?, position_id = ?, education_level_id = ? " +
            "WHERE employee_id = ?";
    /**
     * update record
     *
     * @param entity entity
     */
    @Override
    public void update(Employee entity) {
        ValidatorEmployee.validateEmployeeId(entity.getEmployeeId());

        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(UPDATE_QUERY)) {
            stat.setString(1, entity.getFirstName());
            stat.setString(2, entity.getLastName());
            stat.setInt(3, entity.getPositionId());
            stat.setInt(4, entity.getEducationLevelId());
            stat.setInt(5, entity.getEmployeeId());
            stat.executeUpdate();
            LOGGER.info("Employee by id= {} updated", entity.getEmployeeId());
        } catch (SQLException e) {
            LOGGER.error("Employee update id={}", entity.getEmployeeId(), e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final String DELETE_ALL_QUERY = "DELETE FROM employees";
    /**
     * delete all from Entity
     */
    @Override
    public void deleteAll() {
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(DELETE_ALL_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final String DELETE_BY_ID_QUERY = "DELETE FROM employees where employee_id = ?";
    /**
     * delete where entity_id = id
     *
     * @param id entity_id
     */
    @Override
    public void delete(int id) {
        ValidatorEmployee.validateEmployeeId(id);
        try (Connection conn = ServiceDB.getConnection();
             PreparedStatement stat = conn.prepareStatement(DELETE_BY_ID_QUERY)) {
            stat.setInt(1, id);
            stat.executeUpdate();
            LOGGER.info("Employee id={} deleted", id);
        } catch (SQLException e) {
            LOGGER.error("Employee delete id={}", id, e);
        } catch (IOException e) {
            LOGGER.error("Employee delete id={}", id, e);
        }
    }

    private final String SELECT_EMPLOYEE_ID_QUERY = "SELECT employee_id FROM employees";
    /**
     * get List of Ids
     *
     * @return List of Ids
     */
    @Override
    public List<Integer> getListOfId() {
        try (Connection conn = ServiceDB.getConnection();
             Statement stat = conn.createStatement()) {

            try (ResultSet result = stat.executeQuery(SELECT_EMPLOYEE_ID_QUERY)) {
                List<Integer> resultList = new ArrayList<>();
                while (result.next()) resultList.add(result.getInt("employee_id"));
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
