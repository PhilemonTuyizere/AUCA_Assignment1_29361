package com.auca.student;

import java.time.LocalDate;
import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

public class Student extends AcademicMember {
    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, LocalDate dateOfBirth,
                   String phoneNumber, String email,
                   String lastName, String firstName,
                   String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus,
                   String institutionalEmail, String advisorName,
                   String studentNumber, boolean scholarshipStatus,
                   String guardianPhone, int studyYear, LocalDate admissionDate,
                   String programName) {
        super(personId, dateOfBirth, phoneNumber, email, lastName, firstName,
                memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
        this.studentNumber = studentNumber;
        this.scholarshipStatus = scholarshipStatus;
        this.guardianPhone = guardianPhone;
        this.studyYear = studyYear;
        this.admissionDate = admissionDate;
        this.programName = programName;
    }

    public Student(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus,
                   String institutionalEmail, String advisorName,
                   String studentNumber, String programName,
                   LocalDate admissionDate, int studyYear,
                   String guardianPhone, boolean scholarshipStatus) {
        this(personId, dateOfBirth, phoneNumber, email, lastName, firstName,
                memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName,
                studentNumber, scholarshipStatus, guardianPhone, studyYear, admissionDate, programName);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getProgramName() {
        return programName;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public boolean isScholarshipStatus() {
        return scholarshipStatus;
    }

    public boolean getScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public void setScholarshipStatus(boolean scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    @Override
    public String getRoleDescription() {
        return "Student";
    }

    @Override
    public String getAcademicCategory() {
        return "Learner";
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
        if (studentNumber == null || studentNumber.trim().length() < MIN_ID_LENGTH || studentNumber.trim().length() > MAX_ID_LENGTH) {
            throw new ValidationException("Student number is required and must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }
        if (programName == null || programName.trim().length() < MIN_NAME_LENGTH || programName.trim().length() > 120) {
            throw new ValidationException("Program name must contain 2-120 characters.");
        }
        if (studyYear < 1 || studyYear > 6) {
            throw new ValidationException("Study year must be between 1 and 6.");
        }
        if (admissionDate == null || admissionDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Admission date is required and cannot be in the future.");
        }
        if (guardianPhone == null || !guardianPhone.trim().matches("^\\+?\\d{" + MIN_PHONE_LENGTH + "," + MAX_PHONE_LENGTH + "}$")) {
            throw new ValidationException("Guardian phone must contain " + MIN_PHONE_LENGTH + "-" + MAX_PHONE_LENGTH + " digits, optionally starting with +.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "STUDENT DETAILS\n" +
                super.getRegistrationSummary() + "\n" +
                "Student Number: " + studentNumber + "\n" +
                "Program Name: " + programName + "\n" +
                "Admission Date: " + admissionDate + "\n" +
                "Study Year: " + studyYear + "\n" +
                "Guardian Phone: " + guardianPhone + "\n" +
                "Scholarship: " + (scholarshipStatus ? "YES" : "NO");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
