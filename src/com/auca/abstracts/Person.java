package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, LocalDate dateOfBirth, String phoneNumber,
                  String email, String lastName, String firstName) {
        this.personId = personId;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Person(String personId, String firstName, String lastName,
                  String email, String phoneNumber, LocalDate dateOfBirth) {
        this(personId, dateOfBirth, phoneNumber, email, lastName, firstName);
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public abstract String getRoleDescription();

    @Override
    public abstract boolean isActive();

    @Override
    public String getId() {
        return personId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public void validate() throws ValidationException {
        if (personId == null || personId.trim().length() < MIN_ID_LENGTH || personId.trim().length() > MAX_ID_LENGTH) {
            throw new ValidationException("Person ID is required and must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }
        if (firstName == null || firstName.trim().length() < MIN_NAME_LENGTH || firstName.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException("First name must contain " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " characters.");
        }
        if (lastName == null || lastName.trim().length() < MIN_NAME_LENGTH || lastName.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException("Last name must contain " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " characters.");
        }
        if (email == null || email.trim().length() > MAX_EMAIL_LENGTH || !email.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException("Enter a valid email address (max " + MAX_EMAIL_LENGTH + " chars).");
        }
        if (phoneNumber == null || !phoneNumber.trim().matches("^\\+?\\d{" + MIN_PHONE_LENGTH + "," + MAX_PHONE_LENGTH + "}$")) {
            throw new ValidationException("Phone number must contain " + MIN_PHONE_LENGTH + "-" + MAX_PHONE_LENGTH + " digits, optionally starting with +.");
        }
        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now())) {
            throw new ValidationException("Date of birth must be a valid date in the past.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return "Person ID: " + personId + "\n" +
                "Name: " + getDisplayName() + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phoneNumber + "\n" +
                "Date of Birth: " + dateOfBirth;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
