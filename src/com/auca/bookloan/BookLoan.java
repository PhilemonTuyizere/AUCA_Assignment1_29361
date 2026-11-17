
package com.auca.bookloan;

import com.auca.student.Student;
import com.auca.librarybook.LibraryBook;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;
import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {

    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(String loanId, Student student, LibraryBook book,
                    LocalDate borrowedDate, LocalDate dueDate,
                    LocalDate returnedDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LibraryBook getBook() {
        return book;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public boolean isReturned() {
        return returnedDate != null;
    }

    public void markReturned(LocalDate date) throws ValidationException {
        if (isReturned()) {
            throw new ValidationException("This book has already been returned.");
        }

        if (date == null || date.isBefore(borrowedDate)) {
            throw new ValidationException(
                    "Return date cannot be before the borrowed date."
            );
        }

        returnedDate = date;
        book.returnCopy();
    }

    @Override
    public String getId() {
        return loanId;
    }

    @Override
    public String getDisplayName() {
        return "Loan " + loanId + " - " + book.getTitle();
    }

    @Override
    public boolean isActive() {
        return !isReturned();
    }

    @Override
    public void validate() throws ValidationException {
        InputValidator.requireText(loanId, "Loan ID");

        if (student == null) {
            throw new ValidationException("A loan must belong to a student.");
        }

        if (book == null) {
            throw new ValidationException("A loan must reference a library book.");
        }

        if (borrowedDate == null) {
            throw new ValidationException("Borrowed date is required.");
        }

        InputValidator.validateDateOrder(
                borrowedDate, dueDate, "Due date must be after borrowed date."
        );

        if (returnedDate != null && returnedDate.isBefore(borrowedDate)) {
            throw new ValidationException(
                    "Return date cannot be before borrowed date."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Book Loan: " + loanId
                + " | Student: " + student.getDisplayName()
                + " | Book: " + book.getTitle()
                + " | Borrowed: " + borrowedDate
                + " | Due: " + dueDate
                + " | Returned: "
                + (returnedDate == null ? "Not returned" : returnedDate);
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
