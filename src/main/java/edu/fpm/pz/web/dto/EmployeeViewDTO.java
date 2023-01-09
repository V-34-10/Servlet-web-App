package edu.fpm.pz.web.dto;

import edu.fpm.pz.model.Employee;

import java.io.Serializable;
import java.util.Objects;

public class EmployeeViewDTO implements Serializable {
    private int employeeId;
    private String firstName;
    private String lastName;
    private int positionId;
    private String positionName;
    private int educationLevelId;
    private String educationLevelName;

    public EmployeeViewDTO() {}

    public EmployeeViewDTO(int employeeId,
                    String firstName,
                    String lastName,
                    int positionId,
                    int educationLevelId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionId = positionId;
        this.educationLevelId = educationLevelId;
    }

    public EmployeeViewDTO(int employeeId,
                    String firstName,
                    String lastName,
                    int positionId,
                    String positionName,
                    int educationLevelId,
                    String educationLevelName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionId = positionId;
        this.positionName = positionName;
        this.educationLevelId = educationLevelId;
        this.educationLevelName = educationLevelName;
    }

    public void setEducationLevelId(int educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    public void setEducationLevelName(String educationLevelName) {
        this.educationLevelName = educationLevelName;
    }

    public int getEducationLevelId() {
        return educationLevelId;
    }

    public String getEducationLevelName() {
        return educationLevelName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeViewDTO)) return false;
        EmployeeViewDTO employee = (EmployeeViewDTO) o;
        return positionId == employee.positionId &&
                educationLevelId == employee.educationLevelId &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", educationLevelId='" + educationLevelId + '\'' +
                ", positionId=" + positionId +
                '}';
    }

    public Employee asModel() {
        return new Employee(employeeId, firstName, lastName, positionId, positionName,
                educationLevelId, educationLevelName);
    }


}
