
package com.auca.controller;

import com.auca.abstracts.Person;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.librarybook.LibraryBook;
import com.auca.service.SchoolService;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import com.auca.administrator.Administrator;
import com.auca.view.ConsoleView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchoolController {

    private final SchoolService service;
    private final ConsoleView view;

    public SchoolController(SchoolService service, ConsoleView view) {
        this.service = service;
        this.view = view;
    }

    public void start() {
        view.displayWelcome();

        boolean running = true;

        while (running) {
            view.displayMenu();
            int choice = view.readMenuChoice();

            try {
                switch (choice) {
                    case 1 -> registerDepartment();
                    case 2 -> registerStudent();
                    case 3 -> registerTeacher();
                    case 4 -> registerAdministrator();
                    case 5 -> registerCourse();
                    case 6 -> registerClassroom();
                    case 7 -> enrollStudent();
                    case 8 -> recordAssessment();
                    case 9 -> recordFeePayment();
                    case 10 -> registerLibraryBook();
                    case 11 -> borrowLibraryBook();
                    case 12 -> returnLibraryBook();
                    case 13 -> viewAllRecords();
                    case 14 -> searchRecord();
                    case 15 -> updateRecord();
                    case 16 -> studentAcademicSummary();
                    case 17 -> studentPaymentHistory();
                    case 18 -> viewAvailableBooks();
                    case 19 -> demonstratePolymorphism();
                    case 0 -> {
                        running = false;
                        view.displayGoodbye();
                    }
                    default -> view.displayError("Invalid menu option.");
                }
            } catch (Exception e) {
                view.displayError(e.getMessage() == null
                        ? "An unexpected error occurred."
                        : e.getMessage());
            }

            if (running) {
                view.pause();
            }
        }
    }

    private void registerDepartment() {
        view.displayMessage("REGISTER DEPARTMENT");

        String id = view.readRequiredText("Department ID: ");
        String name = view.readRequiredText("Department name: ");
        String code = view.readRequiredText("Department code: ");
        String office = view.readRequiredText("Office location: ");
        String email = view.readRequiredText("Contact email: ");

        Department department = new Department(
                id, name, code, office, email, null
        );

        reviewAndSave(department, () -> service.registerDepartment(department));
    }

    private void registerStudent() {
        view.displayMessage("+------------------------------------------------------------+");
        view.displayMessage("| AUCA_ID  /  SCHOOL MANAGEMENT                             |");
        view.displayMessage("| STUDENT REGISTRATION                     Step 1 of 4       |");
        view.displayMessage("+------------------------------------------------------------+");
        view.displayMessage("[STEP 1/4] PERSONAL DETAILS");

        String personId = view.readRequiredText("Person ID: ");
        String firstName = view.readRequiredText("First name: ");
        String lastName = view.readRequiredText("Last name: ");
        String email = view.readRequiredText("Personal email: ");
        String phone = view.readRequiredText("Phone number: ");
        LocalDate dob = view.readDate("Date of birth (yyyy-MM-dd): ");

        view.displayMessage("[STEP 2/4] ACADEMIC DETAILS");
        String memberNumber = view.readRequiredText("Member number: ");
        Department department = selectDepartment();
        if (department == null) {
            return;
        }

        LocalDate joinDate = view.readDate("Join date (yyyy-MM-dd): ");
        String academicStatus = view.readChoice(
                "Academic status (ACTIVE/INACTIVE): ",
                "ACTIVE", "INACTIVE"
        );
        String institutionalEmail = view.readRequiredText("Institutional email: ");
        String advisorName = view.readRequiredText("Advisor name: ");

        view.displayMessage("[STEP 3/4] STUDENT DETAILS");
        String studentNumber = view.readRequiredText("Student number: ");
        String programName = view.readRequiredText("Program name: ");
        LocalDate admissionDate = view.readDate("Admission date (yyyy-MM-dd): ");
        int studyYear = view.readInt("Study year (1-8): ", 1, 8);
        String guardianPhone = view.readRequiredText("Guardian phone: ");
        String scholarshipChoice = view.readChoice(
                "Scholarship (1 YES / 2 NO): ",
                "YES", "NO", "1", "2"
        );
        boolean scholarship = scholarshipChoice.equals("YES")
                || scholarshipChoice.equals("1");

        Student student = new Student(
                personId,
                firstName,
                lastName,
                email,
                phone,
                dob,
                memberNumber,
                department,
                joinDate,
                academicStatus,
                institutionalEmail,
                advisorName,
                studentNumber,
                programName,
                admissionDate,
                studyYear,
                guardianPhone,
                scholarship
        );

        reviewAndSave(student, () -> service.registerStudent(student));
    }

    private void registerTeacher() {
        view.displayMessage("REGISTER TEACHER");

        String personId = view.readRequiredText("Person ID: ");
        String firstName = view.readRequiredText("First name: ");
        String lastName = view.readRequiredText("Last name: ");
        String email = view.readRequiredText("Personal email: ");
        String phone = view.readRequiredText("Phone number: ");
        LocalDate dob = view.readDate("Date of birth (yyyy-MM-dd): ");

        String memberNumber = view.readRequiredText("Member number: ");
        Department department = selectDepartment();
        if (department == null) {
            return;
        }

        LocalDate joinDate = view.readDate("Join date (yyyy-MM-dd): ");
        String status = view.readChoice(
                "Academic status (ACTIVE/INACTIVE): ",
                "ACTIVE", "INACTIVE"
        );
        String institutionalEmail = view.readRequiredText("Institutional email: ");
        String advisorName = view.readRequiredText("Advisor name: ");

        String employeeNumber = view.readRequiredText("Employee number: ");
        String specialization = view.readRequiredText("Specialization: ");
        LocalDate hireDate = view.readDate("Hire date (yyyy-MM-dd): ");
        BigDecimal salary = view.readBigDecimal("Salary: ");
        String officeNumber = view.readRequiredText("Office number: ");
        String employmentType = view.readChoice(
                "Employment type (FULL_TIME/PART_TIME/CONTRACT): ",
                "FULL_TIME", "PART_TIME", "CONTRACT"
        );

        Teacher teacher = new Teacher(
                personId,
                firstName,
                lastName,
                email,
                phone,
                dob,
                memberNumber,
                department,
                joinDate,
                status,
                institutionalEmail,
                advisorName,
                employeeNumber,
                specialization,
                hireDate,
                salary,
                officeNumber,
                employmentType
        );

        reviewAndSave(teacher, () -> service.registerTeacher(teacher));
    }

    private void registerAdministrator() {
        view.displayMessage("REGISTER ADMINISTRATOR");

        String personId = view.readRequiredText("Person ID: ");
        String firstName = view.readRequiredText("First name: ");
        String lastName = view.readRequiredText("Last name: ");
        String email = view.readRequiredText("Personal email: ");
        String phone = view.readRequiredText("Phone number: ");
        LocalDate dob = view.readDate("Date of birth (yyyy-MM-dd): ");

        String staffNumber = view.readRequiredText("Staff number: ");
        String jobTitle = view.readRequiredText("Job title: ");
        String office = view.readRequiredText("Assigned office: ");
        LocalDate employmentDate = view.readDate("Employment date (yyyy-MM-dd): ");
        int accessLevel = view.readInt("Access level (1-5): ", 1, 5);
        boolean active = view.readYesNo("Is the administrator active? (YES/NO): ");

        Administrator administrator = new Administrator(
                personId,
                firstName,
                lastName,
                email,
                phone,
                dob,
                staffNumber,
                jobTitle,
                office,
                employmentDate,
                accessLevel,
                active
        );

        reviewAndSave(administrator,
                () -> service.registerAdministrator(administrator));
    }

    private void registerCourse() {
        view.displayMessage("REGISTER COURSE");

        String id = view.readRequiredText("Course ID: ");
        String code = view.readRequiredText("Course code: ");
        String title = view.readRequiredText("Course title: ");
        int credits = view.readInt("Credit hours (1-6): ", 1, 6);

        Department department = selectDepartment();
        if (department == null) {
            return;
        }

        Teacher teacher = selectTeacher();
        if (teacher == null) {
            return;
        }

        Course course = new Course(
                id, code, title, credits, department, teacher
        );

        reviewAndSave(course, () -> service.registerCourse(course));
    }

    private void registerClassroom() {
        view.displayMessage("REGISTER CLASSROOM");

        String id = view.readRequiredText("Classroom ID: ");
        String building = view.readRequiredText("Building name: ");
        String room = view.readRequiredText("Room number: ");
        int capacity = view.readInt("Capacity: ", 1, Integer.MAX_VALUE);
        int floor = view.readInt("Floor number: ", 0, Integer.MAX_VALUE);
        boolean available = view.readYesNo("Is the classroom available? (YES/NO): ");

        Classroom classroom = new Classroom(
                id, building, room, capacity, floor, available
        );

        reviewAndSave(classroom, () -> service.registerClassroom(classroom));
    }

    private void enrollStudent() {
        view.displayMessage("ENROLL STUDENT");

        Student student = selectStudent();
        if (student == null) {
            return;
        }

        Course course = selectCourse();
        if (course == null) {
            return;
        }

        String id = view.readRequiredText("Enrollment ID: ");
        LocalDate date = view.readDate("Enrollment date (yyyy-MM-dd): ");
        String semester = view.readChoice(
                "Semester (SEMESTER_1/SEMESTER_2/SUMMER): ",
                "SEMESTER_1", "SEMESTER_2", "SUMMER"
        );
        String status = view.readChoice(
                "Enrollment status (ACTIVE/COMPLETED/WITHDRAWN): ",
                "ACTIVE", "COMPLETED", "WITHDRAWN"
        );

        Enrollment enrollment = new Enrollment(
                id, student, course, date, semester, status
        );

        reviewAndSave(enrollment, () -> service.registerEnrollment(enrollment));
    }

    private void recordAssessment() {
        view.displayMessage("RECORD ASSESSMENT");

        Enrollment enrollment = selectEnrollment();
        if (enrollment == null) {
            return;
        }

        String id = view.readRequiredText("Assessment ID: ");
        String type = view.readChoice(
                "Assessment type (ASSIGNMENT/QUIZ/MIDTERM/FINAL): ",
                "ASSIGNMENT", "QUIZ", "MIDTERM", "FINAL"
        );
        double maximum = view.readDouble("Maximum score: ");
        double obtained = view.readDouble("Obtained score: ");
        LocalDate date = view.readDate("Assessment date (yyyy-MM-dd): ");

        Assessment assessment = new Assessment(
                id, enrollment, type, maximum, obtained, date
        );

        reviewAndSave(assessment, () -> service.registerAssessment(assessment));
    }

    private void recordFeePayment() {
        view.displayMessage("RECORD FEE PAYMENT");

        Student student = selectStudent();
        if (student == null) {
            return;
        }

        String id = view.readRequiredText("Payment ID: ");
        BigDecimal amount = view.readBigDecimal("Payment amount: ");
        LocalDate date = view.readDate("Payment date (yyyy-MM-dd): ");
        String method = view.readChoice(
                "Payment method (CASH/BANK/MOBILE_MONEY): ",
                "CASH", "BANK", "MOBILE_MONEY"
        );
        String receipt = view.readRequiredText("Receipt number: ");

        FeePayment payment = new FeePayment(
                id, student, amount, date, method, receipt
        );

        reviewAndSave(payment, () -> service.registerPayment(payment));
    }

    private void registerLibraryBook() {
        view.displayMessage("REGISTER LIBRARY BOOK");

        String id = view.readRequiredText("Book ID: ");
        String isbn = view.readRequiredText("ISBN: ");
        String title = view.readRequiredText("Book title: ");
        String author = view.readRequiredText("Author: ");
        int total = view.readInt("Total copies: ", 0, Integer.MAX_VALUE);
        int available = view.readInt(
                "Available copies: ", 0, total
        );

        LibraryBook book = new LibraryBook(
                id, isbn, title, author, total, available
        );

        reviewAndSave(book, () -> service.registerBook(book));
    }

    private void borrowLibraryBook() throws Exception {
        view.displayMessage("BORROW LIBRARY BOOK");

        Student student = selectStudent();
        if (student == null) {
            return;
        }

        LibraryBook book = selectAvailableBook();
        if (book == null) {
            return;
        }

        String loanId = view.readRequiredText("Loan ID: ");
        LocalDate borrowedDate = view.readDate("Borrowed date (yyyy-MM-dd): ");
        LocalDate dueDate = view.readDate("Due date (yyyy-MM-dd): ");

        BookLoan loan = service.borrowBook(
                loanId, student, book, borrowedDate, dueDate
        );

        view.displaySuccess("Book borrowed successfully.");
        view.displayRecord(loan);
    }

    private void returnLibraryBook() throws Exception {
        view.displayMessage("RETURN LIBRARY BOOK");

        List<BookLoan> activeLoans = getRecords(BookLoan.class).stream()
                .filter(loan -> loan.getReturnedDate() == null)
                .toList();

        if (activeLoans.isEmpty()) {
            view.displayWarning("There are no active book loans.");
            return;
        }

        view.displayRecords(activeLoans);

        String loanId = view.readRequiredText("Enter loan ID to return: ");
        LocalDate returnDate = view.readDate("Enter return date (yyyy-MM-dd): ");
        service.returnBook(loanId, returnDate);

        view.displaySuccess("Book returned successfully.");
    }

    private void viewAllRecords() {
        view.displayRecords(service.getAllRecords());
    }

    private void searchRecord() {
        String id = view.readRequiredText("Enter record ID: ");

        for (Object record : service.getAllRecords()) {
            if (record instanceof Identifiable identifiable
                    && identifiable.getId().equalsIgnoreCase(id)) {
                view.displayRecord(record);
                return;
            }
        }

        view.displayWarning("No record found with ID: " + id);
    }

    private void updateRecord() {
        String id = view.readRequiredText("Enter the ID of the record to update: ");
        Object record = findRecord(id);

        if (record == null) {
            view.displayWarning("Record not found.");
            return;
        }

        view.displayRecord(record);

        if (record instanceof Student student) {
            updateStudent(student);
        } else if (record instanceof Person person) {
            updatePerson(person);
        } else if (record instanceof Department department) {
            updateDepartment(department);
        } else if (record instanceof Course course) {
            updateCourse(course);
        } else if (record instanceof Classroom classroom) {
            updateClassroom(classroom);
        } else if (record instanceof LibraryBook book) {
            updateLibraryBook(book);
        } else {
            view.displayWarning("This record type does not have an update option yet.");
        }
    }

    private void updateStudent(Student student) {
        view.displayMessage("UPDATE STUDENT");
        view.displayMessage("1. Student number");
        view.displayMessage("2. Program name");
        view.displayMessage("3. Study year");
        view.displayMessage("4. Guardian phone");
        view.displayMessage("5. Scholarship");

        int choice = view.readInt("Choose a field: ", 1, 5);

        try {
            switch (choice) {
                case 1 -> {
                    String old = student.getStudentNumber();
                    String value = view.readRequiredText("Enter the new student number: ");
                    student.setStudentNumber(value);
                    validateUpdate(student, () -> student.setStudentNumber(old));
                }
                case 2 -> {
                    String old = student.getProgramName();
                    String value = view.readRequiredText("Enter the new program name: ");
                    student.setProgramName(value);
                    validateUpdate(student, () -> student.setProgramName(old));
                }
                case 3 -> {
                    int old = student.getStudyYear();
                    int value = view.readInt("Enter study year (1-8): ", 1, 8);
                    student.setStudyYear(value);
                    validateUpdate(student, () -> student.setStudyYear(old));
                }
                case 4 -> {
                    String old = student.getGuardianPhone();
                    String value = view.readRequiredText("Enter the guardian phone: ");
                    student.setGuardianPhone(value);
                    validateUpdate(student, () -> student.setGuardianPhone(old));
                }
                case 5 -> {
                    boolean old = student.getScholarshipStatus();
                    String value = view.readChoice(
                            "Scholarship (1 YES / 2 NO): ",
                            "YES", "NO", "1", "2"
                    );
                    student.setScholarshipStatus(value.equals("YES") || value.equals("1"));
                    validateUpdate(student, () -> student.setScholarshipStatus(old));
                }
            }
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }

    private void updatePerson(Person person) {
        view.displayMessage("UPDATE PERSON");
        view.displayMessage("1. First name");
        view.displayMessage("2. Last name");
        view.displayMessage("3. Email");
        view.displayMessage("4. Phone number");

        int choice = view.readInt("Choose a field: ", 1, 4);

        String oldValue;
        String newValue = view.readRequiredText("Enter the new value: ");

        try {
            switch (choice) {
                case 1 -> {
                    oldValue = person.getFirstName();
                    person.setFirstName(newValue);
                    validateUpdate(person, () -> person.setFirstName(oldValue));
                }
                case 2 -> {
                    oldValue = person.getLastName();
                    person.setLastName(newValue);
                    validateUpdate(person, () -> person.setLastName(oldValue));
                }
                case 3 -> {
                    oldValue = person.getEmail();
                    person.setEmail(newValue);
                    validateUpdate(person, () -> person.setEmail(oldValue));
                }
                case 4 -> {
                    oldValue = person.getPhoneNumber();
                    person.setPhoneNumber(newValue);
                    validateUpdate(person, () -> person.setPhoneNumber(oldValue));
                }
                default -> view.displayError("Invalid selection.");
            }
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }

    private void updateDepartment(Department department) {
        view.displayMessage("1. Department name");
        view.displayMessage("2. Office location");
        view.displayMessage("3. Contact email");

        int choice = view.readInt("Choose a field: ", 1, 3);
        String value = view.readRequiredText("Enter the new value: ");

        try {
            switch (choice) {
                case 1 -> {
                    String old = department.getDepartmentName();
                    department.setDepartmentName(value);
                    validateUpdate(department, () -> department.setDepartmentName(old));
                }
                case 2 -> {
                    String old = department.getOfficeLocation();
                    department.setOfficeLocation(value);
                    validateUpdate(department, () -> department.setOfficeLocation(old));
                }
                case 3 -> {
                    String old = department.getContactEmail();
                    department.setContactEmail(value);
                    validateUpdate(department, () -> department.setContactEmail(old));
                }
            }
        } catch (Exception e) {
            view.displayError(e.getMessage());
        }
    }

    private void updateCourse(Course course) {
        String old = course.getCourseTitle();
        String value = view.readRequiredText("Enter the new course title: ");

        course.setCourseTitle(value);
        validateUpdate(course, () -> course.setCourseTitle(old));
    }

    private void updateClassroom(Classroom classroom) {
        int old = classroom.getCapacity();
        int value = view.readInt("Enter the new capacity: ", 1, Integer.MAX_VALUE);

        classroom.setCapacity(value);
        validateUpdate(classroom, () -> classroom.setCapacity(old));
    }

    private void updateLibraryBook(LibraryBook book) {
        int old = book.getTotalCopies();
        int value = view.readInt("Enter the new total copies: ", 0, Integer.MAX_VALUE);

        book.setTotalCopies(value);
        validateUpdate(book, () -> book.setTotalCopies(old));
    }

    private void validateUpdate(Object record, Runnable rollback) {
        try {
            if (record instanceof Student student) {
                student.validate();
            } else if (record instanceof Person person) {
                person.validate();
            } else if (record instanceof Department department) {
                department.validate();
            } else if (record instanceof Course course) {
                course.validate();
            } else if (record instanceof Classroom classroom) {
                classroom.validate();
            } else if (record instanceof LibraryBook book) {
                book.validate();
            }

            view.displaySuccess("Record updated successfully.");
        } catch (ValidationException e) {
            rollback.run();
            view.displayError("Update rejected: " + e.getMessage());
        }
    }

    private void studentAcademicSummary() {
        Student student = selectStudent();
        if (student == null) {
            return;
        }

        view.displayMessage("STUDENT ACADEMIC SUMMARY");
        view.displayRecord(student);

        List<Enrollment> enrollments = getRecords(Enrollment.class).stream()
                .filter(e -> e.getStudent().equals(student))
                .toList();

        view.displayMessage("ENROLLMENTS");
        view.displayRecords(enrollments);

        List<Assessment> assessments = getRecords(Assessment.class).stream()
                .filter(a -> a.getEnrollment().getStudent().equals(student))
                .toList();

        view.displayMessage("ASSESSMENTS");
        view.displayRecords(assessments);
    }

    private void studentPaymentHistory() {
        Student student = selectStudent();
        if (student == null) {
            return;
        }

        List<FeePayment> payments = getRecords(FeePayment.class).stream()
                .filter(payment -> payment.getStudent().equals(student))
                .toList();

        view.displayMessage("PAYMENT HISTORY FOR " + student.getDisplayName());
        view.displayRecords(payments);

        BigDecimal total = payments.stream()
                .map(FeePayment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        view.displayMessage("Total payments: " + total);
    }

    private void viewAvailableBooks() {
        List<LibraryBook> books = getRecords(LibraryBook.class).stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .toList();

        view.displayRecords(books);
    }

    private void demonstratePolymorphism() {
        view.displayMessage("POLYMORPHISM DEMONSTRATION");

        List<Identifiable> identifiableRecords = new ArrayList<>();

        for (Object record : service.getAllRecords()) {
            if (record instanceof Identifiable identifiable) {
                identifiableRecords.add(identifiable);
            }
        }

        for (Identifiable record : identifiableRecords) {
            System.out.println(
                    record.getId() + " | "
                            + record.getDisplayName() + " | Active: "
                            + record.isActive()
            );
        }

        view.displayMessage("PERSON POLYMORPHISM");

        for (Object record : service.getAllRecords()) {
            if (record instanceof Person person) {
                System.out.println(
                        person.getRoleDescription() + ": "
                                + person.getRegistrationSummary()
                );
            }
        }
    }

    private Department selectDepartment() {
        List<Department> departments = getRecords(Department.class);

        if (departments.isEmpty()) {
            view.displayWarning("No department is registered.");

            if (view.readYesNo("Register a department now? (YES/NO): ")) {
                registerDepartment();
                departments = getRecords(Department.class);
            }
        }

        return selectFromList(departments, "DEPARTMENTS");
    }

    private Teacher selectTeacher() {
        List<Teacher> teachers = getRecords(Teacher.class);

        if (teachers.isEmpty()) {
            view.displayWarning("No teacher is registered.");

            if (view.readYesNo("Register a teacher now? (YES/NO): ")) {
                registerTeacher();
                teachers = getRecords(Teacher.class);
            }
        }

        return selectFromList(teachers, "TEACHERS");
    }

    private Student selectStudent() {
        List<Student> students = getRecords(Student.class);

        if (students.isEmpty()) {
            view.displayWarning("No student is registered.");

            if (view.readYesNo("Register a student now? (YES/NO): ")) {
                registerStudent();
                students = getRecords(Student.class);
            }
        }

        return selectFromList(students, "STUDENTS");
    }

    private Course selectCourse() {
        List<Course> courses = getRecords(Course.class);

        if (courses.isEmpty()) {
            view.displayWarning("No course is registered.");

            if (view.readYesNo("Register a course now? (YES/NO): ")) {
                registerCourse();
                courses = getRecords(Course.class);
            }
        }

        return selectFromList(courses, "COURSES");
    }

    private Enrollment selectEnrollment() {
        List<Enrollment> enrollments = getRecords(Enrollment.class).stream()
                .filter(e -> "ACTIVE".equalsIgnoreCase(e.getEnrollmentStatus()))
                .toList();

        return selectFromList(enrollments, "ACTIVE ENROLLMENTS");
    }

    private LibraryBook selectAvailableBook() {
        List<LibraryBook> books = getRecords(LibraryBook.class).stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .toList();

        return selectFromList(books, "AVAILABLE BOOKS");
    }

    private <T> T selectFromList(List<T> records, String title) {
        if (records.isEmpty()) {
            view.displayWarning("No records available for " + title + ".");
            return null;
        }

        view.displayOptions(records, title);

        int choice = view.readInt(
                "Select a number (0 to cancel): ",
                0,
                records.size()
        );

        if (choice == 0) {
            return null;
        }

        return records.get(choice - 1);
    }

    private Object findRecord(String id) {
        for (Object record : service.getAllRecords()) {
            if (record instanceof Identifiable identifiable
                    && identifiable.getId().equalsIgnoreCase(id)) {
                return record;
            }
        }

        return null;
    }

    private <T> List<T> getRecords(Class<T> type) {
        List<T> matchingRecords = new ArrayList<>();

        for (Object record : service.getAllRecords()) {
            if (type.isInstance(record)) {
                matchingRecords.add(type.cast(record));
            }
        }

        return matchingRecords;
    }

    private void reviewAndSave(Object record, RegistrationAction action) {
        view.displayMessage("REVIEW RECORD");
        view.displayRecord(record);

        while (true) {
            String choice = view.readChoice(
                    "Enter SAVE, CANCEL, or EDIT: ",
                    "SAVE", "CANCEL", "EDIT"
            );

            if (choice.equals("CANCEL")) {
                view.displayWarning("Registration cancelled. No record was saved.");
                return;
            }

            if (choice.equals("EDIT")) {
                view.displayWarning(
                        "Please cancel and restart registration to correct the entered values."
                );
                return;
            }

            try {
                action.execute();
                view.displaySuccess("Record registered successfully.");
                return;
            } catch (Exception e) {
                view.displayError(e.getMessage());
                return;
            }
        }
    }

    @FunctionalInterface
    private interface RegistrationAction {
        void execute() throws Exception;
    }
}
