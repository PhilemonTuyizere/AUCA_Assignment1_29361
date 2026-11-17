
package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;
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
            String personId, String firstName, String lastName,
            String email, String phoneNumber, LocalDate dateOfBirth,
            String memberNumber, Department department, LocalDate joinDate,
            String academicStatus, String institutionalEmail, String advisorName,
            String employeeNumber, String specialization, LocalDate hireDate,
            BigDecimal salary, String officeNumber, String employmentType) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth,
                memberNumber, department, joinDate, academicStatus,
                institutionalEmail, advisorName);

        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    @Override
    public String getAcademicCategory() {
        return "TEACHER";
    }

    @Override
    public String getRoleDescription() {
        return "Teacher";
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(getAcademicStatus());
    }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateLength(getPersonId(), "Person ID", 2, 30);
        InputValidator.validateLength(getFirstName(), "First name", 2, 60);
        InputValidator.validateLength(getLastName(), "Last name", 2, 60);
        InputValidator.validateEmail(getEmail(), "Email");
        InputValidator.validatePhone(getPhoneNumber(), "Phone number");
        InputValidator.validatePastDate(getDateOfBirth(), "Date of birth");

        InputValidator.requireText(getMemberNumber(), "Member number");

        if (getDepartment() == null) {
            throw new ValidationException("Teacher must belong to a department.");
        }

        InputValidator.validateNotFutureDate(getJoinDate(), "Join date");
        InputValidator.validateAllowedValue(
                getAcademicStatus(), "Academic status",
                "ACTIVE", "INACTIVE", "SUSPENDED"
        );
        InputValidator.validateEmail(getInstitutionalEmail(), "Institutional email");
        InputValidator.requireText(getAdvisorName(), "Advisor name");

        InputValidator.requireText(employeeNumber, "Employee number");
        InputValidator.validateLength(specialization, "Specialization", 2, 100);
        InputValidator.validateNotFutureDate(hireDate, "Hire date");
        InputValidator.validatePositiveAmount(salary, "Salary");
        InputValidator.requireText(officeNumber, "Office number");
        InputValidator.validateAllowedValue(
                employmentType, "Employment type",
                "FULL_TIME", "PART_TIME", "CONTRACT"
        );
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Teacher: " + getDisplayName()
                + " | ID: " + getPersonId()
                + " | Employee Number: " + employeeNumber
                + " | Specialization: " + specialization
                + " | Department: " + getDepartment().getDepartmentName()
                + " | Salary: " + salary
                + " | Status: " + getAcademicStatus();
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
