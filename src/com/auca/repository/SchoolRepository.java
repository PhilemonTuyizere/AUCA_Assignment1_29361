
package com.auca.repository;

import com.auca.abstracts.Person;
import com.auca.interfaces.Identifiable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SchoolRepository {

    private final List<Department> departments = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Teacher> teachers = new ArrayList<>();
    private final List<Administrator> administrators = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Classroom> classrooms = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<Assessment> assessments = new ArrayList<>();
    private final List<FeePayment> payments = new ArrayList<>();
    private final List<LibraryBook> books = new ArrayList<>();
    private final List<BookLoan> loans = new ArrayList<>();

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Administrator> getAdministrators() {
        return administrators;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public List<FeePayment> getPayments() {
        return payments;
    }

    public List<LibraryBook> getBooks() {
        return books;
    }

    public List<BookLoan> getLoans() {
        return loans;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void addAdministrator(Administrator administrator) {
        administrators.add(administrator);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public void addPayment(FeePayment payment) {
        payments.add(payment);
    }

    public void addBook(LibraryBook book) {
        books.add(book);
    }

    public void addLoan(BookLoan loan) {
        loans.add(loan);
    }

    public Optional<Department> findDepartment(String id) {
        return departments.stream()
                .filter(d -> d.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Student> findStudent(String id) {
        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id)
                        || s.getStudentNumber().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Teacher> findTeacher(String id) {
        return teachers.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id)
                        || t.getEmployeeNumber().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Administrator> findAdministrator(String id) {
        return administrators.stream()
                .filter(a -> a.getId().equalsIgnoreCase(id)
                        || a.getStaffNumber().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Course> findCourse(String id) {
        return courses.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id)
                        || c.getCourseCode().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Classroom> findClassroom(String id) {
        return classrooms.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Enrollment> findEnrollment(String id) {
        return enrollments.stream()
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<Assessment> findAssessment(String id) {
        return assessments.stream()
                .filter(a -> a.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<FeePayment> findPayment(String id) {
        return payments.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id)
                        || p.getReceiptNumber().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<LibraryBook> findBook(String id) {
        return books.stream()
                .filter(b -> b.getId().equalsIgnoreCase(id)
                        || b.getIsbn().equalsIgnoreCase(id))
                .findFirst();
    }

    public Optional<BookLoan> findLoan(String id) {
        return loans.stream()
                .filter(l -> l.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public boolean idExists(String id) {
        return getAllRecords().stream()
                .anyMatch(record -> record.getId().equalsIgnoreCase(id));
    }

    public List<Identifiable> getAllRecords() {
        List<Identifiable> records = new ArrayList<>();

        records.addAll(departments);
        records.addAll(students);
        records.addAll(teachers);
        records.addAll(administrators);
        records.addAll(courses);
        records.addAll(classrooms);
        records.addAll(enrollments);
        records.addAll(assessments);
        records.addAll(payments);
        records.addAll(books);
        records.addAll(loans);

        return records;
    }

    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);
        return people;
    }

    public boolean studentNumberExists(String number) {
        return students.stream()
                .anyMatch(s -> s.getStudentNumber().equalsIgnoreCase(number));
    }

    public boolean employeeNumberExists(String number) {
        return teachers.stream()
                .anyMatch(t -> t.getEmployeeNumber().equalsIgnoreCase(number));
    }

    public boolean courseCodeExists(String code) {
        return courses.stream()
                .anyMatch(c -> c.getCourseCode().equalsIgnoreCase(code));
    }

    public boolean receiptExists(String receipt) {
        return payments.stream()
                .anyMatch(p -> p.getReceiptNumber().equalsIgnoreCase(receipt));
    }

    public boolean memberNumberExists(String number) {
        return teachers.stream().anyMatch(t ->
                t.getMemberNumber().equalsIgnoreCase(number))
                || students.stream().anyMatch(s ->
                s.getMemberNumber().equalsIgnoreCase(number));
    }


    public boolean emailExists(String email) {
        return getAllPeople().stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(email));
    }

}
