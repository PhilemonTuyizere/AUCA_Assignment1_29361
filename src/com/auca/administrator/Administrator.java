package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public class Administrator extends Person {

    private String staffNumber;
    private String jobTitle;
    private String assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator(
            String personId,
            LocalDate dateOfBirth,
            String phoneNumber,
            String email,
            String lastName,
            String firstName,
            String staffNumber,
            String jobTitle,
            String assignedOffice,
            LocalDate employmentDate,
            int accessLevel,
            boolean active) {

        super(personId, dateOfBirth, phoneNumber, email, lastName, firstName);

        this.staffNumber = staffNumber;
        this.jobTitle = jobTitle;
        this.assignedOffice = assignedOffice;
        this.employmentDate = employmentDate;
        this.accessLevel = accessLevel;
        this.active = active;
    }

    public Administrator(
            String personId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            String staffNumber,
            String jobTitle,
            String assignedOffice,
            LocalDate employmentDate,
            int accessLevel,
            boolean active) {

        this(personId, dateOfBirth, phoneNumber, email, lastName, firstName,
                staffNumber, jobTitle, assignedOffice, employmentDate, accessLevel, active);
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getAssignedOffice() {
        return assignedOffice;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public boolean getActive() {
        return active;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        return active;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator";
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (staffNumber == null || staffNumber.trim().length() < MIN_ID_LENGTH || staffNumber.trim().length() > MAX_ID_LENGTH) {
            throw new ValidationException("Staff number is required and must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }

        if (jobTitle == null || jobTitle.trim().length() < MIN_NAME_LENGTH || jobTitle.trim().length() > 120) {
            throw new ValidationException("Job title must contain 2-120 characters.");
        }

        if (assignedOffice == null || assignedOffice.trim().isEmpty()) {
            throw new ValidationException("Assigned office is required.");
        }

        if (employmentDate == null || employmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Employment date is required and cannot be in the future.");
        }

        if (accessLevel < 1) {
            throw new ValidationException("Access level must be at least 1.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "ADMINISTRATOR DETAILS\n" +
                super.getRegistrationSummary() + "\n" +
                "Staff Number: " + staffNumber + "\n" +
                "Job Title: " + jobTitle + "\n" +
                "Assigned Office: " + assignedOffice + "\n" +
                "Employment Date: " + employmentDate + "\n" +
                "Access Level: " + accessLevel + "\n" +
                "Active: " + (active ? "YES" : "NO");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
