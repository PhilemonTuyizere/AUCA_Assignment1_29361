package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Teacher extends AcademicMember {

    private String employeeNumber;
    private String specialization;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String officeNumber;
    private String employmentType;

    public Teacher(
            String personId,
            LocalDate dateOfBirth,
            String phoneNumber,
            String email,
            String lastName,
            String firstName,
            String memberNumber,
            Department department,
            LocalDate joinDate,
            String academicStatus,
            String institutionalEmail,
            String advisorName,
            String employeeNumber,
            String specialization,
            LocalDate hireDate,
            BigDecimal salary,
            String officeNumber,
            String employmentType) {

        super(personId, dateOfBirth, phoneNumber, email,
                lastName, firstName, memberNumber, department,
                joinDate, academicStatus, institutionalEmail, advisorName);

        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }

    public Teacher(
            String personId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            String memberNumber,
            Department department,
            LocalDate joinDate,
            String academicStatus,
            String institutionalEmail,
            String advisorName,
            String employeeNumber,
            String specialization,
            LocalDate hireDate,
            BigDecimal salary,
            String officeNumber,
            String employmentType) {

        this(personId, dateOfBirth, phoneNumber, email,
                lastName, firstName, memberNumber, department,
                joinDate, academicStatus, institutionalEmail, advisorName,
                employeeNumber, specialization, hireDate, salary, officeNumber, employmentType);
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    @Override
    public String getRoleDescription() {
        return "Teacher";
    }

    @Override
    public String getAcademicCategory() {
        return "Academic Staff";
    }

    @Override
    public String getId() {
        return getPersonId();
    }

    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(getAcademicStatus());
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (employeeNumber == null || employeeNumber.trim().length() < MIN_ID_LENGTH || employeeNumber.trim().length() > MAX_ID_LENGTH) {
            throw new ValidationException("Employee number is required and must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }

        if (specialization == null || specialization.trim().length() < MIN_NAME_LENGTH || specialization.trim().length() > 120) {
            throw new ValidationException("Specialization must contain 2-120 characters.");
        }

        if (hireDate == null || hireDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Hire date is required and cannot be in the future.");
        }

        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Salary must be greater than zero.");
        }

        if (officeNumber == null || officeNumber.trim().isEmpty()) {
            throw new ValidationException("Office number is required.");
        }

        if (employmentType == null || employmentType.trim().isEmpty()) {
            throw new ValidationException("Employment type is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "TEACHER DETAILS\n" +
                super.getRegistrationSummary() + "\n" +
                "Employee Number: " + employeeNumber + "\n" +
                "Specialization: " + specialization + "\n" +
                "Hire Date: " + hireDate + "\n" +
                "Salary: " + salary + "\n" +
                "Office Number: " + officeNumber + "\n" +
                "Employment Type: " + employmentType;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
