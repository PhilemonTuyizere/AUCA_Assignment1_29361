
package com.auca.course;

import com.auca.department.Department;
import com.auca.teacher.Teacher;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;

public class Course implements Identifiable, Registrable {

    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(String courseId, String courseCode, String courseTitle,
                  int creditHours, Department department, Teacher assignedTeacher) {
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

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getAssignedTeacher() {
        return assignedTeacher;
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
        return courseTitle;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        InputValidator.requireText(courseId, "Course ID");
        InputValidator.requireText(courseCode, "Course code");
        InputValidator.validateLength(courseTitle, "Course title", 2, 100);
        InputValidator.validateRange(creditHours, "Credit hours", 1, 6);

        if (department == null) {
            throw new ValidationException("A course must belong to a department.");
        }

        if (assignedTeacher == null) {
            throw new ValidationException("A course must have an assigned teacher.");
        }

        if (!assignedTeacher.isActive()) {
            throw new ValidationException("The assigned teacher is not active.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Course: " + courseTitle
                + " | ID: " + courseId
                + " | Code: " + courseCode
                + " | Credit Hours: " + creditHours
                + " | Department: " + department.getDepartmentName()
                + " | Teacher: " + assignedTeacher.getDisplayName();
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
