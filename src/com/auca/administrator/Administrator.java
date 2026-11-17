
package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;
import java.time.LocalDate;

public class Administrator extends Person {

    private String staffNumber;
    private String jobTitle;
    private String assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator(
            String personId, String firstName, String lastName,
            String email, String phoneNumber, LocalDate dateOfBirth,
            String staffNumber, String jobTitle, String assignedOffice,
            LocalDate employmentDate, int accessLevel, boolean active) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);

        this.staffNumber = staffNumber;
        this.jobTitle = jobTitle;
        this.assignedOffice = assignedOffice;
        this.employmentDate = employmentDate;
        this.accessLevel = accessLevel;
        this.active = active;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAssignedOffice() {
        return assignedOffice;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator";
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateLength(getPersonId(), "Person ID", 2, 30);
        InputValidator.validateLength(getFirstName(), "First name", 2, 60);
        InputValidator.validateLength(getLastName(), "Last name", 2, 60);
        InputValidator.validateEmail(getEmail(), "Email");
        InputValidator.validatePhone(getPhoneNumber(), "Phone number");
        InputValidator.validatePastDate(getDateOfBirth(), "Date of birth");

        InputValidator.requireText(staffNumber, "Staff number");
        InputValidator.validateLength(jobTitle, "Job title", 2, 60);
        InputValidator.requireText(assignedOffice, "Assigned office");
        InputValidator.validateNotFutureDate(employmentDate, "Employment date");
        InputValidator.validateRange(accessLevel, "Access level", 1, 5);
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Administrator: " + getDisplayName()
                + " | ID: " + getPersonId()
                + " | Staff Number: " + staffNumber
                + " | Job Title: " + jobTitle
                + " | Office: " + assignedOffice
                + " | Access Level: " + accessLevel
                + " | Active: " + active;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
