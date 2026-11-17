package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;

import java.time.LocalDate;

public abstract class AcademicMember extends Person {

    private String memberNumber;
    private Department department;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(
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
            String advisorName) {

        super(
                personId,
                firstName,
                lastName,
                email,
                phoneNumber,
                dateOfBirth
        );

        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public abstract String getAcademicCategory();

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateLength(getPersonId(), "Person ID", 2, 30);
        InputValidator.validateLength(getFirstName(), "First name", 2, 60);
        InputValidator.validateLength(getLastName(), "Last name", 2, 60);
        InputValidator.validateEmail(getEmail(), "Email");
        InputValidator.validatePhone(getPhoneNumber(), "Phone number");
        InputValidator.validatePastDate(getDateOfBirth(), "Date of birth");

        InputValidator.requireText(memberNumber, "Member number");
        if (department == null) {
            throw new ValidationException("Academic member must belong to a department.");
        }
        InputValidator.validateNotFutureDate(joinDate, "Join date");
        InputValidator.validateAllowedValue(
                academicStatus,
                "Academic status",
                "ACTIVE",
                "INACTIVE",
                "SUSPENDED"
        );
        InputValidator.validateEmail(institutionalEmail, "Institutional email");
        InputValidator.validateLength(advisorName, "Advisor name", 2, 120);
    }
}