package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.exceptions.BookUnavailableException;

public class LibraryBook implements Identifiable, Registrable {

    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(
            String bookId,
            String isbn,
            String title,
            String author,
            int totalCopies,
            int availableCopies) {

        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public LibraryBook(
            String bookId,
            String isbn,
            String title,
            String author,
            int totalCopies) {

        this(bookId, isbn, title, author, totalCopies, totalCopies);
    }

    public String getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    private void decrementStock() throws BookUnavailableException {
        if (availableCopies <= 0) {
            throw new BookUnavailableException(
                    "No available copies of " + title + "."
            );
        }
        availableCopies--;
    }

    private void incrementStock() throws ValidationException {
        if (availableCopies >= totalCopies) {
            throw new ValidationException(
                    "Cannot return this book because all copies are already available."
            );
        }
        availableCopies++;
    }

    @Override
    public void borrowCopy() throws BookUnavailableException {
        decrementStock();
    }

    @Override
    public void returnCopy() throws ValidationException {
        incrementStock();
    }

    @Override
    public String getId() {
        return bookId;
    }

    @Override
    public String getDisplayName() {
        return title + " by " + author;
    }

    @Override
    public boolean isActive() {
        return availableCopies > 0;
    }

    @Override
    public void validate() throws ValidationException {

        if (bookId == null || bookId.trim().isEmpty()) {
            throw new ValidationException("Book ID is required.");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ValidationException("ISBN is required.");
        }

        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Book title is required.");
        }

        if (author == null || author.trim().length() < MIN_NAME_LENGTH || author.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException("Book author must contain " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " characters.");
        }

        if (totalCopies < 0) {
            throw new ValidationException("Total copies cannot be negative.");
        }

        if (availableCopies < 0 || availableCopies > totalCopies) {
            throw new ValidationException(
                    "Available copies must be between zero and total copies."
            );
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "LIBRARY BOOK DETAILS\n" +
                "Book ID: " + bookId + "\n" +
                "ISBN: " + isbn + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Total Copies: " + totalCopies + "\n" +
                "Available Copies: " + availableCopies;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
