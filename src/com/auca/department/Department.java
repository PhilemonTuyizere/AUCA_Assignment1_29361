package com.auca.department;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;
import com.auca.exceptions.ValidationException;

public class Department implements Identifiable, Registrable {

    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(
            String departmentId,
            String departmentName,
            String departmentCode,
            String officeLocation,
            String contactEmail,
            Teacher headTeacher) {

        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    public Department(
            String departmentId,
            String departmentName,
            String departmentCode,
            String officeLocation,
            String contactEmail) {

        this(departmentId, departmentName, departmentCode, officeLocation, contactEmail, null);
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Teacher getHeadTeacher() {
        return headTeacher;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    @Override
    public String getId() {
        return departmentId;
    }

    @Override
    public String getDisplayName() {
        return departmentName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (departmentId == null || departmentId.trim().isEmpty()) {
            throw new ValidationException("Department ID is required.");
        }

        if (departmentName == null ||
                departmentName.trim().length() < MIN_NAME_LENGTH ||
                departmentName.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException(
                    "Department name must contain " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " characters."
            );
        }

        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            throw new ValidationException("Department code is required.");
        }

        if (officeLocation == null || officeLocation.trim().isEmpty()) {
            throw new ValidationException("Office location is required.");
        }

        if (contactEmail == null || contactEmail.trim().length() > MAX_EMAIL_LENGTH ||
                !contactEmail.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException("Enter a valid department email.");
        }

        String headTeacherDepartmentId = headTeacher == null || headTeacher.getDepartment() == null
                ? null
                : headTeacher.getDepartment().getId();
        if (headTeacher != null &&
                (departmentId == null || headTeacherDepartmentId == null ||
                 !departmentId.equalsIgnoreCase(headTeacherDepartmentId))) {
            throw new ValidationException(
                    "The head teacher must belong to this department."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        String teacherName = headTeacher == null
                ? "Not assigned"
                : headTeacher.getDisplayName();

        return "DEPARTMENT DETAILS\n" +
                "Department ID: " + departmentId + "\n" +
                "Department Name: " + departmentName + "\n" +
                "Department Code: " + departmentCode + "\n" +
                "Office Location: " + officeLocation + "\n" +
                "Contact Email: " + contactEmail + "\n" +
                "Head Teacher: " + teacherName;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
