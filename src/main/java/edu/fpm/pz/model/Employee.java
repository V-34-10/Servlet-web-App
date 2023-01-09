package edu.fpm.pz.model;

import edu.fpm.pz.web.dto.EmployeeViewDTO;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {
    private int employeeId;
    private String firstName;
    private String lastName;
    private int positionId;
    private int educationLevelId;
    private String positionName;
    private String educationLevelName;

    public Employee() {
    }

    public Employee(int employeeId,
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

    public Employee(int employeeId,
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

    public Employee(int employeeId,
                    String firstName,
                    String lastName,
                    int positionId,
                    String positionName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public int getEducationLevelId() {
        return educationLevelId;
    }

    public String getEducationLevelName() {
        return educationLevelName;
    }

    public void setEducationLevelId(int educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    public void setEducationLevelName(String educationLevelName) {
        this.educationLevelName = educationLevelName;
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
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", positionId=" + positionId +
                ", educationLevelId=" + educationLevelId +
                ", positionName='" + positionName + '\'' +
                ", educationLevelName='" + educationLevelName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && positionId == employee.positionId
                && educationLevelId == employee.educationLevelId && firstName.equals(employee.firstName)
                && lastName.equals(employee.lastName) && positionName.equals(employee.positionName)
                && educationLevelName.equals(employee.educationLevelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, positionId, educationLevelId, positionName, educationLevelName);
    }

    public EmployeeViewDTO asViewDTO() {
        return new EmployeeViewDTO(employeeId, firstName, lastName, positionId, positionName, educationLevelId, educationLevelName);
    }


}
