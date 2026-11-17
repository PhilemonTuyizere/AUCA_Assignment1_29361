package com.auca.feepayment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FeePayment implements Identifiable, Registrable {

    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String receiptNumber;

    private static final List<String> ALLOWED_METHODS =
            Arrays.asList("CASH", "BANK", "MOBILE MONEY", "CARD");

    public FeePayment(
            String paymentId,
            Student student,
            BigDecimal amount,
            LocalDate paymentDate,
            String paymentMethod,
            String receiptNumber) {

        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Student getStudent() {
        return student;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String getId() {
        return paymentId;
    }

    @Override
    public String getDisplayName() {
        return "Payment " + paymentId + " - " +
                (student == null ? "No student" : student.getDisplayName());
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new ValidationException("Payment ID is required.");
        }

        if (student == null) {
            throw new ValidationException("A registered student must be selected.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Payment amount must be greater than zero.");
        }

        if (paymentDate == null || paymentDate.isAfter(LocalDate.now())) {
            throw new ValidationException(
                    "Payment date is required and cannot be in the future."
            );
        }

        if (paymentMethod == null ||
                !ALLOWED_METHODS.contains(paymentMethod.trim().toUpperCase())) {
            throw new ValidationException(
                    "Payment method must be CASH, BANK, MOBILE MONEY or CARD."
            );
        }

        if (receiptNumber == null || receiptNumber.trim().isEmpty()) {
            throw new ValidationException("Receipt number is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "FEE PAYMENT DETAILS\n" +
                "Payment ID: " + paymentId + "\n" +
                "Student: " + (student == null ? "None" : student.getDisplayName()) + "\n" +
                "Amount: " + amount + "\n" +
                "Payment Date: " + paymentDate + "\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Receipt Number: " + receiptNumber;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
