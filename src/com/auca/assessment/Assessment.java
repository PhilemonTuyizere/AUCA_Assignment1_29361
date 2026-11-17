package com.auca.assessment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Assessment implements Identifiable, Registrable {

    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    private static final List<String> ALLOWED_TYPES =
            Arrays.asList("QUIZ", "ASSIGNMENT", "MIDTERM", "FINAL", "PROJECT");

    public Assessment(
            String assessmentId,
            Enrollment enrollment,
            String assessmentType,
            double maximumScore,
            double obtainedScore,
            LocalDate assessmentDate) {

        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public double getMaximumScore() {
        return maximumScore;
    }

    public double getObtainedScore() {
        return obtainedScore;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setMaximumScore(double maximumScore) {
        this.maximumScore = maximumScore;
    }

    public void setObtainedScore(double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    @Override
    public String getId() {
        return assessmentId;
    }

    @Override
    public String getDisplayName() {
        return assessmentType + " - " +
                (enrollment == null ? "No enrollment" : enrollment.getDisplayName());
    }

    @Override
    public boolean isActive() {
        return enrollment != null && enrollment.isActive();
    }

    @Override
    public void validate() throws ValidationException {

        if (assessmentId == null || assessmentId.trim().isEmpty()) {
            throw new ValidationException("Assessment ID is required.");
        }

        if (enrollment == null) {
            throw new ValidationException("An enrollment must be selected.");
        }

        if (!enrollment.isActive()) {
            throw new ValidationException(
                    "Assessment can only be recorded for an active enrollment."
            );
        }

        if (assessmentType == null ||
                !ALLOWED_TYPES.contains(assessmentType.trim().toUpperCase())) {
            throw new ValidationException(
                    "Assessment type must be QUIZ, ASSIGNMENT, MIDTERM, FINAL or PROJECT."
            );
        }

        if (maximumScore <= 0) {
            throw new ValidationException("Maximum score must be greater than zero.");
        }

        if (obtainedScore < 0 || obtainedScore > maximumScore) {
            throw new ValidationException(
                    "Obtained score must be between zero and maximum score."
            );
        }

        if (assessmentDate == null || assessmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Assessment date is required and cannot be in the future."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "ASSESSMENT DETAILS\n" +
                "Assessment ID: " + assessmentId + "\n" +
                "Enrollment: " + (enrollment == null ? "None" : enrollment.getId() + " (" + enrollment.getDisplayName() + ")") + "\n" +
                "Assessment Type: " + assessmentType + "\n" +
                "Maximum Score: " + maximumScore + "\n" +
                "Obtained Score: " + obtainedScore + "\n" +
                "Assessment Date: " + assessmentDate;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
