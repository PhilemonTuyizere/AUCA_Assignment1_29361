package com.auca.bookloan;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.librarybook.LibraryBook;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {

    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(
            String loanId,
            Student student,
            LibraryBook book,
            LocalDate borrowedDate,
            LocalDate dueDate,
            LocalDate returnedDate) {

        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public BookLoan(
            String loanId,
            Student student,
            LibraryBook book,
            LocalDate borrowedDate,
            LocalDate dueDate) {

        this(loanId, student, book, borrowedDate, dueDate, null);
    }

    public String getLoanId() {
        return loanId;
    }

    public Student getStudent() {
        return student;
    }

    public LibraryBook getBook() {
        return book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public String getId() {
        return loanId;
    }

    @Override
    public String getDisplayName() {
        return (student == null ? "Unknown student" : student.getDisplayName()) +
                " - " +
                (book == null ? "Unknown book" : book.getTitle());
    }

    @Override
    public boolean isActive() {
        return returnedDate == null;
    }

    @Override
    public void validate() throws ValidationException {

        if (loanId == null || loanId.trim().isEmpty()) {
            throw new ValidationException("Loan ID is required.");
        }

        if (student == null) {
            throw new ValidationException("A registered student must be selected.");
        }

        if (book == null) {
            throw new ValidationException("A registered library book must be selected.");
        }

        if (borrowedDate == null || borrowedDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Borrowed date is required and cannot be in the future.");
        }

        if (dueDate == null || !dueDate.isAfter(borrowedDate)) {
            throw new ValidationException(
                    "Due date must be after the borrowed date."
            );
        }

        if (returnedDate != null && returnedDate.isBefore(borrowedDate)) {
            throw new ValidationException(
                    "Returned date cannot be before the borrowed date."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "BOOK LOAN DETAILS\n" +
                "Loan ID: " + loanId + "\n" +
                "Student: " + (student == null ? "None" : student.getDisplayName()) + "\n" +
                "Book: " + (book == null ? "None" : book.getTitle()) + "\n" +
                "Borrowed Date: " + borrowedDate + "\n" +
                "Due Date: " + dueDate + "\n" +
                "Returned Date: " +
                (returnedDate == null ? "Not returned" : returnedDate);
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
