
package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;
import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {

    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment,
                      String assessmentType, double maximumScore,
                      double obtainedScore, LocalDate assessmentDate) {
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

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public double getMaximumScore() {
        return maximumScore;
    }

    public void setMaximumScore(double maximumScore) {
        this.maximumScore = maximumScore;
    }

    public double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
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
        return assessmentType + " - " + assessmentId;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        InputValidator.requireText(assessmentId, "Assessment ID");

        if (enrollment == null) {
            throw new ValidationException("Assessment requires an enrollment.");
        }

        if (!enrollment.isActive()) {
            throw new ValidationException(
                    "Assessment can only be recorded for an active enrollment."
            );
        }

        InputValidator.validateAllowedValue(
                assessmentType, "Assessment type",
                "QUIZ", "ASSIGNMENT", "MIDTERM", "FINAL", "PROJECT", "PRESENTATION"
        );

        InputValidator.validatePositive(maximumScore, "Maximum score");
        InputValidator.validateScore(obtainedScore, maximumScore);
        InputValidator.validateNotFutureDate(assessmentDate, "Assessment date");
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Assessment: " + assessmentId
                + " | Student: " + enrollment.getStudent().getDisplayName()
                + " | Course: " + enrollment.getCourse().getCourseTitle()
                + " | Type: " + assessmentType
                + " | Score: " + obtainedScore + "/" + maximumScore
                + " | Date: " + assessmentDate;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
