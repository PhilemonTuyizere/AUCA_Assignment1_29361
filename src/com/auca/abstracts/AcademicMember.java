package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class AcademicMember extends Person {
    private String memberNumber;
    private Department department;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(String personId, LocalDate dateOfBirth, String phoneNumber, String email,
                          String lastName, String firstName, String memberNumber, Department department,
                          LocalDate joinDate, String academicStatus, String institutionalEmail, String advisorName) {
        super(personId, dateOfBirth, phoneNumber, email, lastName, firstName);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public AcademicMember(String personId, String firstName, String lastName, String email,
                          String phoneNumber, LocalDate dateOfBirth, String memberNumber, Department department,
                          LocalDate joinDate, String academicStatus, String institutionalEmail, String advisorName) {
        this(personId, dateOfBirth, phoneNumber, email, lastName, firstName,
                memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
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
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(academicStatus);
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (memberNumber == null || memberNumber.trim().length() < MIN_ID_LENGTH || memberNumber.trim().length() > MAX_ID_LENGTH) {
            throw new ValidationException("Member number is required and must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }
        if (department == null) {
            throw new ValidationException("A registered department must be selected.");
        }
        if (joinDate == null || joinDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Join date is required and cannot be in the future.");
        }
        if (academicStatus == null || (!"ACTIVE".equalsIgnoreCase(academicStatus.trim()) && !"INACTIVE".equalsIgnoreCase(academicStatus.trim()))) {
            throw new ValidationException("Academic status must be ACTIVE or INACTIVE.");
        }
        if (institutionalEmail == null || institutionalEmail.trim().length() > MAX_EMAIL_LENGTH || !institutionalEmail.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException("Enter a valid institutional email address.");
        }
        if (advisorName == null || advisorName.trim().length() < MIN_NAME_LENGTH || advisorName.trim().length() > 120) {
            throw new ValidationException("Advisor name must contain 2-120 characters.");
        }
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary() + "\n" +
                "Member Number: " + memberNumber + "\n" +
                "Department: " + (department == null ? "Not assigned" : "[" + department.getDepartmentCode() + "] " + department.getDepartmentName()) + "\n" +
                "Join Date: " + joinDate + "\n" +
                "Academic Status: " + academicStatus + "\n" +
                "Institutional Email: " + institutionalEmail + "\n" +
                "Advisor Name: " + advisorName;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
