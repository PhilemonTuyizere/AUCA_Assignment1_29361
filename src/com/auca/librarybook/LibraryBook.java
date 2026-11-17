
package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.validation.InputValidator;

public class LibraryBook implements Identifiable, Registrable {

    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String bookId, String isbn, String title,
                       String author, int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public boolean borrowCopy() {
        if (availableCopies <= 0) {
            return false;
        }
        availableCopies--;
        return true;
    }

    public void returnCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public String getId() {
        return bookId;
    }

    @Override
    public String getDisplayName() {
        return title;
    }

    @Override
    public boolean isActive() {
        return availableCopies > 0;
    }

    @Override
    public void validate() throws ValidationException {
        InputValidator.requireText(bookId, "Book ID");
        InputValidator.requireText(isbn, "ISBN");
        InputValidator.validateLength(title, "Book title", 2, 150);
        InputValidator.validateLength(author, "Author", 2, 100);
        InputValidator.validateNonNegative(totalCopies, "Total copies");
        InputValidator.validateNonNegative(availableCopies, "Available copies");

        if (availableCopies > totalCopies) {
            throw new ValidationException(
                    "Available copies cannot exceed total copies."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Book: " + title
                + " | ID: " + bookId
                + " | ISBN: " + isbn
                + " | Author: " + author
                + " | Total Copies: " + totalCopies
                + " | Available Copies: " + availableCopies;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
