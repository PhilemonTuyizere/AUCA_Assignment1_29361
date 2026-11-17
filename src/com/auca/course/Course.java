package com.auca.course;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.department.Department;
import com.auca.teacher.Teacher;
import com.auca.exceptions.ValidationException;

public class Course implements Identifiable, Registrable {

    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(
            String courseId,
            String courseCode,
            String courseTitle,
            int creditHours,
            Department department,
            Teacher assignedTeacher) {

        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.department = department;
        this.assignedTeacher = assignedTeacher;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public Department getDepartment() {
        return department;
    }

    public Teacher getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setAssignedTeacher(Teacher assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    @Override
    public String getId() {
        return courseId;
    }

    @Override
    public String getDisplayName() {
        return courseCode + " - " + courseTitle;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (courseId == null || courseId.trim().isEmpty()) {
            throw new ValidationException("Course ID is required.");
        }

        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new ValidationException("Course code is required.");
        }

        if (courseTitle == null ||
                courseTitle.trim().length() < MIN_NAME_LENGTH ||
                courseTitle.trim().length() > 120) {
            throw new ValidationException(
                    "Course title must contain 2-120 characters."
            );
        }

        if (creditHours < 1 || creditHours > 6) {
            throw new ValidationException(
                    "Credit hours must be between 1 and 6."
            );
        }

        if (department == null) {
            throw new ValidationException(
                    "A registered department must be selected."
            );
        }

        if (assignedTeacher == null) {
            throw new ValidationException(
                    "A registered teacher must be selected."
            );
        }

        if (assignedTeacher.getDepartment() == null) {
            throw new ValidationException(
                    "The assigned teacher must belong to an existing department."
            );
        }

        String teacherDepartmentId = assignedTeacher.getDepartment().getId();
        String courseDepartmentId = department.getId();
        if (teacherDepartmentId == null || courseDepartmentId == null ||
                !teacherDepartmentId.equalsIgnoreCase(courseDepartmentId)) {
            throw new ValidationException(
                    "The assigned teacher must belong to the selected department."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        String departmentName = department == null
                ? "Not assigned"
                : department.getDepartmentName();

        String teacherName = assignedTeacher == null
                ? "Not assigned"
                : assignedTeacher.getDisplayName();

        return "COURSE DETAILS\n" +
                "Course ID: " + courseId + "\n" +
                "Course Code: " + courseCode + "\n" +
                "Course Title: " + courseTitle + "\n" +
                "Credit Hours: " + creditHours + "\n" +
                "Department: " + departmentName + "\n" +
                "Assigned Teacher: " + teacherName;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
