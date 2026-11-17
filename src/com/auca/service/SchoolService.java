
package com.auca.service;

import com.auca.repository.SchoolRepository;
import com.auca.exceptions.ValidationException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.BookUnavailableException;

import com.auca.student.Student;
import com.auca.teacher.Teacher;
import com.auca.administrator.Administrator;
import com.auca.department.Department;
import com.auca.course.Course;
import com.auca.classroom.Classroom;
import com.auca.enrollment.Enrollment;
import com.auca.assessment.Assessment;
import com.auca.feepayment.FeePayment;
import com.auca.librarybook.LibraryBook;
import com.auca.bookloan.BookLoan;
import com.auca.interfaces.Identifiable;
import com.auca.abstracts.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SchoolService {

    private final SchoolRepository repository;

    public SchoolService(SchoolRepository repository) {
        this.repository = repository;
    }

    private void checkId(String id) throws DuplicateRecordException {
        if (repository.idExists(id)) {
            throw new DuplicateRecordException("ID already exists: " + id);
        }
    }

    public void registerDepartment(Department department)
            throws ValidationException, DuplicateRecordException {
        department.validate();
        checkId(department.getId());

        if (repository.getDepartments().stream()
                .anyMatch(d -> d.getDepartmentCode()
                        .equalsIgnoreCase(department.getDepartmentCode()))) {
            throw new DuplicateRecordException("Department code already exists.");
        }

        if (department.getHeadTeacher() != null
                && !repository.getTeachers().contains(department.getHeadTeacher())) {
            throw new ValidationException("Head teacher is not registered.");
        }

        repository.addDepartment(department);
    }

    public void registerStudent(Student student)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException {
        if (repository.getDepartments().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No department exists. Register a department first."
            );
        }

        student.validate();
        checkId(student.getId());

        if (!repository.getDepartments().contains(student.getDepartment())) {
            throw new ValidationException("Select a registered department.");
        }

        if (repository.studentNumberExists(student.getStudentNumber())) {
            throw new DuplicateRecordException("Student number already exists.");
        }

        if (repository.memberNumberExists(student.getMemberNumber())) {
            throw new DuplicateRecordException("Member number already exists.");
        }

        if (repository.emailExists(student.getEmail())) {
            throw new DuplicateRecordException("Email already exists.");
        }

        repository.addStudent(student);
    }

    public void registerTeacher(Teacher teacher)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException {
        if (repository.getDepartments().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No department exists. Register a department first."
            );
        }

        teacher.validate();
        checkId(teacher.getId());

        if (!repository.getDepartments().contains(teacher.getDepartment())) {
            throw new ValidationException("Select a registered department.");
        }

        if (repository.employeeNumberExists(teacher.getEmployeeNumber())) {
            throw new DuplicateRecordException("Employee number already exists.");
        }

        if (repository.memberNumberExists(teacher.getMemberNumber())) {
            throw new DuplicateRecordException("Member number already exists.");
        }

        if (repository.emailExists(teacher.getEmail())) {
            throw new DuplicateRecordException("Email already exists.");
        }

        repository.addTeacher(teacher);
    }

    public void registerAdministrator(Administrator administrator)
            throws ValidationException, DuplicateRecordException {
        administrator.validate();
        checkId(administrator.getId());

        if (repository.getAdministrators().stream()
                .anyMatch(a -> a.getStaffNumber()
                        .equalsIgnoreCase(administrator.getStaffNumber()))) {
            throw new DuplicateRecordException("Staff number already exists.");
        }

        if (repository.emailExists(administrator.getEmail())) {
            throw new DuplicateRecordException("Email already exists.");
        }

        repository.addAdministrator(administrator);
    }

    public void registerCourse(Course course)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException {
        if (repository.getDepartments().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No department exists. Register a department first."
            );
        }

        if (repository.getTeachers().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No teacher exists. Register a teacher first."
            );
        }

        course.validate();
        checkId(course.getId());

        if (!repository.getDepartments().contains(course.getDepartment())) {
            throw new ValidationException("Select a registered department.");
        }

        if (!repository.getTeachers().contains(course.getAssignedTeacher())) {
            throw new ValidationException("Select a registered teacher.");
        }

        if (repository.courseCodeExists(course.getCourseCode())) {
            throw new DuplicateRecordException("Course code already exists.");
        }

        repository.addCourse(course);
    }

    public void registerClassroom(Classroom classroom)
            throws ValidationException, DuplicateRecordException {
        classroom.validate();
        checkId(classroom.getId());
        repository.addClassroom(classroom);
    }

    public void registerEnrollment(Enrollment enrollment)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException {
        if (repository.getStudents().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No student exists. Register a student first."
            );
        }

        if (repository.getCourses().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No course exists. Register a course before enrolling a student."
            );
        }

        enrollment.validate();
        checkId(enrollment.getId());

        if (!repository.getStudents().contains(enrollment.getStudent())) {
            throw new ValidationException("Select a registered student.");
        }

        if (!repository.getCourses().contains(enrollment.getCourse())) {
            throw new ValidationException("Select a registered course.");
        }

        boolean duplicate = repository.getEnrollments().stream()
                .anyMatch(e -> e.isActive()
                        && e.getStudent().equals(enrollment.getStudent())
                        && e.getCourse().equals(enrollment.getCourse())
                        && e.getSemester().equalsIgnoreCase(enrollment.getSemester()));

        if (duplicate) {
            throw new DuplicateRecordException(
                    "This student is already actively enrolled in this course and semester."
            );
        }

        repository.addEnrollment(enrollment);
    }

    public void registerAssessment(Assessment assessment)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException {
        if (repository.getEnrollments().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No enrollment exists. Enroll a student before recording an assessment."
            );
        }

        assessment.validate();
        checkId(assessment.getId());

        if (!repository.getEnrollments().contains(assessment.getEnrollment())) {
            throw new ValidationException("Select a registered enrollment.");
        }

        repository.addAssessment(assessment);
    }

    public void registerPayment(FeePayment payment)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException {
        if (repository.getStudents().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No student exists. Register a student before recording payment."
            );
        }

        payment.validate();
        checkId(payment.getId());

        if (!repository.getStudents().contains(payment.getStudent())) {
            throw new ValidationException("Select a registered student.");
        }

        if (repository.receiptExists(payment.getReceiptNumber())) {
            throw new DuplicateRecordException("Receipt number already exists.");
        }

        repository.addPayment(payment);
    }

    public void registerBook(LibraryBook book)
            throws ValidationException, DuplicateRecordException {
        book.validate();
        checkId(book.getId());

        if (repository.getBooks().stream()
                .anyMatch(b -> b.getIsbn().equalsIgnoreCase(book.getIsbn()))) {
            throw new DuplicateRecordException("ISBN already exists.");
        }

        repository.addBook(book);
    }

    public BookLoan borrowBook(String loanId, Student student,
                               LibraryBook book, LocalDate borrowedDate,
                               LocalDate dueDate)
            throws ValidationException, DuplicateRecordException,
            PrerequisiteMissingException, BookUnavailableException {

        if (repository.getStudents().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No student exists. Register a student first."
            );
        }

        if (repository.getBooks().isEmpty()) {
            throw new PrerequisiteMissingException(
                    "No library book exists. Register a book first."
            );
        }

        checkId(loanId);

        if (!repository.getStudents().contains(student)) {
            throw new ValidationException("Select a registered student.");
        }

        if (!repository.getBooks().contains(book)) {
            throw new ValidationException("Select a registered book.");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("No copies of this book are available.");
        }

        BookLoan loan = new BookLoan(
                loanId, student, book, borrowedDate, dueDate, null
        );

        loan.validate();

        if (!book.borrowCopy()) {
            throw new BookUnavailableException("The book is no longer available.");
        }

        repository.addLoan(loan);
        return loan;
    }

    public void returnBook(String loanId, LocalDate returnDate)
            throws RecordNotFoundException, ValidationException {
        BookLoan loan = repository.findLoan(loanId)
                .orElseThrow(() ->
                        new RecordNotFoundException("Loan not found: " + loanId));

        loan.markReturned(returnDate);
    }

    public void assignDepartmentHead(String departmentId, String teacherId)
            throws RecordNotFoundException, ValidationException {
        Department department = repository.findDepartment(departmentId)
                .orElseThrow(() -> new RecordNotFoundException("Department not found."));

        Teacher teacher = repository.findTeacher(teacherId)
                .orElseThrow(() -> new RecordNotFoundException("Teacher not found."));

        if (!teacher.isActive()) {
            throw new ValidationException("An inactive teacher cannot head a department.");
        }

        if (!teacher.getDepartment().equals(department)) {
            throw new ValidationException(
                    "The head teacher must belong to this department."
            );
        }

        department.setHeadTeacher(teacher);
    }

    public List<Identifiable> getAllRecords() {
        return repository.getAllRecords();
    }

    public List<Person> getAllPeople() {
        return repository.getAllPeople();
    }

    public Optional<Identifiable> searchById(String id) {
        return repository.getAllRecords().stream()
                .filter(record -> record.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<FeePayment> getStudentPayments(String studentId)
            throws RecordNotFoundException {
        Student student = repository.findStudent(studentId)
                .orElseThrow(() ->
                        new RecordNotFoundException("Student not found: " + studentId));

        return repository.getPayments().stream()
                .filter(p -> p.getStudent().equals(student))
                .toList();
    }

    public List<Enrollment> getStudentEnrollments(String studentId)
            throws RecordNotFoundException {
        Student student = repository.findStudent(studentId)
                .orElseThrow(() ->
                        new RecordNotFoundException("Student not found: " + studentId));

        return repository.getEnrollments().stream()
                .filter(e -> e.getStudent().equals(student))
                .toList();
    }

    public List<Assessment> getStudentAssessments(String studentId)
            throws RecordNotFoundException {
        Student student = repository.findStudent(studentId)
                .orElseThrow(() ->
                        new RecordNotFoundException("Student not found: " + studentId));

        return repository.getAssessments().stream()
                .filter(a -> a.getEnrollment().getStudent().equals(student))
                .toList();
    }

    public List<LibraryBook> getAvailableBooks() {
        return repository.getBooks().stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .toList();
    }

    public SchoolRepository getRepository() {
        return repository;
    }
}
