
package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;

import java.time.LocalDate;

public class Student extends AcademicMember {

    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(
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
            String studentNumber,
            String programName,
            LocalDate admissionDate,
            int studyYear,
            String guardianPhone,
            boolean scholarshipStatus
    ) {
        super(
                personId,
                firstName,
                lastName,
                email,
                phoneNumber,
                dateOfBirth,
                memberNumber,
                department,
                joinDate,
                academicStatus,
                institutionalEmail,
                advisorName
        );

        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public boolean getScholarshipStatus() {
        return scholarshipStatus;
    }

    public boolean isScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setScholarshipStatus(boolean scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    @Override
    public String getAcademicCategory() {
        return "STUDENT";
    }

    @Override
    public String getRoleDescription() {
        return "Student";
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(getAcademicStatus());
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        InputValidator.validateAllowedValue(
                getAcademicStatus(),
                "Academic status",
                "ACTIVE",
                "INACTIVE"
        );

        InputValidator.requireText(studentNumber, "Student number");
        InputValidator.validateLength(studentNumber, "Student number", 4, 30);

        InputValidator.requireText(programName, "Program name");
        InputValidator.validateLength(programName, "Program name", 2, 120);

        InputValidator.validateNotFutureDate(admissionDate, "Admission date");

        InputValidator.validateRange(studyYear, "Study year", 1, 8);

        InputValidator.validatePhone(guardianPhone, "Guardian phone");
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Student: " + getFirstName() + " " + getLastName()
                + " | Student Number: " + studentNumber
                + " | Program: " + programName
                + " | Study Year: " + studyYear
                + " | Department: " + getDepartment().getDepartmentName()
                + " | Status: " + getAcademicStatus();
    }

    @Override
    public String toString() {
        return "Student Details\n"
                + "Person ID: " + getPersonId() + "\n"
                + "Name: " + getFirstName() + " " + getLastName() + "\n"
                + "Email: " + getEmail() + "\n"
                + "Phone: " + getPhoneNumber() + "\n"
                + "Date of Birth: " + getDateOfBirth() + "\n"
                + "Member Number: " + getMemberNumber() + "\n"
                + "Department: " + getDepartment().getDepartmentName() + "\n"
                + "Join Date: " + getJoinDate() + "\n"
                + "Academic Status: " + getAcademicStatus() + "\n"
                + "Institutional Email: " + getInstitutionalEmail() + "\n"
                + "Advisor Name: " + getAdvisorName() + "\n"
                + "Student Number: " + studentNumber + "\n"
                + "Program Name: " + programName + "\n"
                + "Admission Date: " + admissionDate + "\n"
                + "Study Year: " + studyYear + "\n"
                + "Guardian Phone: " + guardianPhone + "\n"
                + "Scholarship Status: " + (scholarshipStatus ? "YES" : "NO");
    }
}
