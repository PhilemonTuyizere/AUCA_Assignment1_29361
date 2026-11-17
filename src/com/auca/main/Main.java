package com.auca.main;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.abstracts.Person;
import com.auca.abstracts.AcademicMember;
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

import com.auca.exceptions.ValidationException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.BookUnavailableException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> payments = new ArrayList<>();
    private static final List<LibraryBook> books = new ArrayList<>();
    private static final List<BookLoan> loans = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("       AUCA_ID SCHOOL MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        boolean running = true;

        while (running) {
            displayMenu();

            int choice = readMenuChoice("Select an option: ");

            try {
                switch (choice) {
                    case 1:
                        registerDepartmentFlow(false);
                        break;
                    case 2:
                        registerStudentFlow(false);
                        break;
                    case 3:
                        registerTeacherFlow(false);
                        break;
                    case 4:
                        registerAdministratorFlow(false);
                        break;
                    case 5:
                        registerCourseFlow(false);
                        break;
                    case 6:
                        registerClassroomFlow(false);
                        break;
                    case 7:
                        enrollStudentFlow(false);
                        break;
                    case 8:
                        recordAssessmentFlow(false);
                        break;
                    case 9:
                        recordFeePaymentFlow(false);
                        break;
                    case 10:
                        registerLibraryBookFlow(false);
                        break;
                    case 11:
                        borrowBookFlow();
                        break;
                    case 12:
                        returnBookFlow();
                        break;
                    case 13:
                        viewAllRecords();
                        break;
                    case 14:
                        searchRecord();
                        break;
                    case 15:
                        updateRecord();
                        break;
                    case 16:
                        showStudentAcademicSummary();
                        break;
                    case 17:
                        showStudentPaymentHistory();
                        break;
                    case 18:
                        viewAvailableBooks();
                        break;
                    case 19:
                        demonstratePolymorphism();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using AUCA_ID.");
                        break;
                    default:
                        System.out.println("Invalid option. Select a number from 0 to 19.");
                }

            } catch (ValidationException |
                      DuplicateRecordException |
                     RecordNotFoundException |
                     PrerequisiteMissingException |
                     BookUnavailableException e) {

                System.out.println("\nOperation failed: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("\nUnexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println(" 1. Register Department       11. Borrow Library Book");
        System.out.println(" 2. Register Student          12. Return Library Book");
        System.out.println(" 3. Register Teacher          13. View All Records");
        System.out.println(" 4. Register Administrator    14. Search Record by ID");
        System.out.println(" 5. Register Course           15. Update Existing Record");
        System.out.println(" 6. Register Classroom        16. Student Academic Summary");
        System.out.println(" 7. Enroll Student            17. Student Payment History");
        System.out.println(" 8. Record Assessment         18. View Available Books");
        System.out.println(" 9. Record Fee Payment        19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book      0. Exit");
        System.out.println("========================================================");
    }

    private static int readMenuChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Select a number from 0 to 19.");
            }
        }
    }

    private static String readRaw(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static boolean confirm(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String ans = scanner.nextLine().trim();
            if (ans.equalsIgnoreCase("Y")) {
                return true;
            }
            if (ans.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Enter Y or N.");
        }
    }

    private static List<Identifiable> getAllRecords() {
        List<Identifiable> all = new ArrayList<>();
        all.addAll(departments);
        all.addAll(students);
        all.addAll(teachers);
        all.addAll(administrators);
        all.addAll(courses);
        all.addAll(classrooms);
        all.addAll(enrollments);
        all.addAll(assessments);
        all.addAll(payments);
        all.addAll(books);
        all.addAll(loans);
        return all;
    }

    private static boolean isIdUnique(String id, Identifiable current) {
        for (Identifiable item : getAllRecords()) {
            if (item != current && item.getId().equalsIgnoreCase(id)) {
                return false;
            }
        }
        return true;
    }

    private static void checkUniqueId(String id) throws DuplicateRecordException {
        if (!isIdUnique(id, null)) {
            throw new DuplicateRecordException("A record with ID " + id + " already exists.");
        }
    }

    private static boolean isEmailUnique(String email, Person current) {
        for (Student s : students) {
            if (s != current && s.getEmail().equalsIgnoreCase(email)) return false;
        }
        for (Teacher t : teachers) {
            if (t != current && t.getEmail().equalsIgnoreCase(email)) return false;
        }
        for (Administrator a : administrators) {
            if (a != current && a.getEmail().equalsIgnoreCase(email)) return false;
        }
        return true;
    }

    private static boolean isMemberNumberUnique(String memberNo, AcademicMember current) {
        for (Student s : students) {
            if (s != current && s.getMemberNumber().equalsIgnoreCase(memberNo)) return false;
        }
        for (Teacher t : teachers) {
            if (t != current && t.getMemberNumber().equalsIgnoreCase(memberNo)) return false;
        }
        return true;
    }

    private static boolean isInstEmailUnique(String instEmail, AcademicMember current) {
        for (Student s : students) {
            if (s != current && s.getInstitutionalEmail().equalsIgnoreCase(instEmail)) return false;
        }
        for (Teacher t : teachers) {
            if (t != current && t.getInstitutionalEmail().equalsIgnoreCase(instEmail)) return false;
        }
        return true;
    }

    private static boolean isStudentNumberUnique(String sn, Student current) {
        for (Student s : students) {
            if (s != current && s.getStudentNumber().equalsIgnoreCase(sn)) return false;
        }
        return true;
    }

    private static boolean isEmployeeNumberUnique(String en, Teacher current) {
        for (Teacher t : teachers) {
            if (t != current && t.getEmployeeNumber().equalsIgnoreCase(en)) return false;
        }
        return true;
    }

    private static boolean isStaffNumberUnique(String sn, Administrator current) {
        for (Administrator a : administrators) {
            if (a != current && a.getStaffNumber().equalsIgnoreCase(sn)) return false;
        }
        return true;
    }

    private static boolean isDepartmentCodeUnique(String dc, Department current) {
        for (Department d : departments) {
            if (d != current && d.getDepartmentCode().equalsIgnoreCase(dc)) return false;
        }
        return true;
    }

    private static boolean isCourseCodeUnique(String cc, Course current) {
        for (Course c : courses) {
            if (c != current && c.getCourseCode().equalsIgnoreCase(cc)) return false;
        }
        return true;
    }

    private static boolean isReceiptNumberUnique(String rn, FeePayment current) {
        for (FeePayment p : payments) {
            if (p != current && p.getReceiptNumber().equalsIgnoreCase(rn)) return false;
        }
        return true;
    }

    private static boolean isIsbnUnique(String isbn, LibraryBook current) {
        for (LibraryBook b : books) {
            if (b != current && b.getIsbn().equalsIgnoreCase(isbn)) return false;
        }
        return true;
    }

    private static Department selectDepartmentWithAdd() throws DuplicateRecordException {
        while (true) {
            System.out.println("+----------------- CHOOSE DEPARTMENT ------------------------+");
            if (departments.isEmpty()) {
                System.out.println("| No department registered.                                  |");
                System.out.println("| 1 [+] Register a new Department                            |");
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                if (input.equals("1")) {
                    Department created = registerDepartmentFlow(true);
                    if (created != null) {
                        return created;
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                for (int i = 0; i < departments.size(); i++) {
                    Department d = departments.get(i);
                    String line = String.format("| %d [%s] %s", (i + 1), d.getDepartmentCode(), d.getDepartmentName());
                    while (line.length() < 61) line += " ";
                    line += "|";
                    System.out.println(line);
                }
                int addOption = departments.size() + 1;
                String addLine = String.format("| %d [+] Register a new Department", addOption);
                while (addLine.length() < 61) addLine += " ";
                addLine += "|";
                System.out.println(addLine);
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                try {
                    int val = Integer.parseInt(input);
                    if (val >= 1 && val <= departments.size()) {
                        return departments.get(val - 1);
                    }
                    if (val == addOption) {
                        Department created = registerDepartmentFlow(true);
                        if (created != null) {
                            return created;
                        }
                    } else {
                        System.out.println("Invalid department selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric input.");
                }
            }
        }
    }

    private static Teacher selectTeacherWithAdd(Department deptFilter) throws DuplicateRecordException {
        while (true) {
            List<Teacher> available = new ArrayList<>();
            for (Teacher t : teachers) {
                if (deptFilter == null || (t.getDepartment() != null && t.getDepartment().getId().equalsIgnoreCase(deptFilter.getId()))) {
                    available.add(t);
                }
            }
            System.out.println("+----------------- CHOOSE TEACHER ---------------------------+");
            if (available.isEmpty()) {
                System.out.println("| No teacher registered.                                     |");
                System.out.println("| 1 [+] Register a new Teacher                               |");
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                if (input.equals("1")) {
                    Teacher created = registerTeacherFlow(true);
                    if (created != null) {
                        return created;
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                for (int i = 0; i < available.size(); i++) {
                    Teacher t = available.get(i);
                    String line = String.format("| %d [%s] %s", (i + 1), t.getEmployeeNumber(), t.getDisplayName());
                    while (line.length() < 61) line += " ";
                    line += "|";
                    System.out.println(line);
                }
                int addOption = available.size() + 1;
                String addLine = String.format("| %d [+] Register a new Teacher", addOption);
                while (addLine.length() < 61) addLine += " ";
                addLine += "|";
                System.out.println(addLine);
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                try {
                    int val = Integer.parseInt(input);
                    if (val >= 1 && val <= available.size()) {
                        return available.get(val - 1);
                    }
                    if (val == addOption) {
                        Teacher created = registerTeacherFlow(true);
                        if (created != null) {
                            return created;
                        }
                    } else {
                        System.out.println("Invalid teacher selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric input.");
                }
            }
        }
    }

    private static Student selectStudentWithAdd() throws DuplicateRecordException {
        while (true) {
            System.out.println("+----------------- CHOOSE STUDENT ---------------------------+");
            if (students.isEmpty()) {
                System.out.println("| No student registered.                                     |");
                System.out.println("| 1 [+] Register a new Student                               |");
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                if (input.equals("1")) {
                    Student created = registerStudentFlow(true);
                    if (created != null) {
                        return created;
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                for (int i = 0; i < students.size(); i++) {
                    Student s = students.get(i);
                    String line = String.format("| %d [%s] %s", (i + 1), s.getStudentNumber(), s.getDisplayName());
                    while (line.length() < 61) line += " ";
                    line += "|";
                    System.out.println(line);
                }
                int addOption = students.size() + 1;
                String addLine = String.format("| %d [+] Register a new Student", addOption);
                while (addLine.length() < 61) addLine += " ";
                addLine += "|";
                System.out.println(addLine);
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                try {
                    int val = Integer.parseInt(input);
                    if (val >= 1 && val <= students.size()) {
                        return students.get(val - 1);
                    }
                    if (val == addOption) {
                        Student created = registerStudentFlow(true);
                        if (created != null) {
                            return created;
                        }
                    } else {
                        System.out.println("Invalid student selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric input.");
                }
            }
        }
    }

    private static Course selectCourseWithAdd() throws DuplicateRecordException {
        while (true) {
            System.out.println("+----------------- CHOOSE COURSE ----------------------------+");
            if (courses.isEmpty()) {
                System.out.println("| No course registered.                                      |");
                System.out.println("| 1 [+] Register a new Course                                |");
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                if (input.equals("1")) {
                    Course created = registerCourseFlow(true);
                    if (created != null) {
                        return created;
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                for (int i = 0; i < courses.size(); i++) {
                    Course c = courses.get(i);
                    String line = String.format("| %d [%s] %s", (i + 1), c.getCourseCode(), c.getCourseTitle());
                    while (line.length() < 61) line += " ";
                    line += "|";
                    System.out.println(line);
                }
                int addOption = courses.size() + 1;
                String addLine = String.format("| %d [+] Register a new Course", addOption);
                while (addLine.length() < 61) addLine += " ";
                addLine += "|";
                System.out.println(addLine);
                System.out.println("| 0 Cancel registration                                      |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                try {
                    int val = Integer.parseInt(input);
                    if (val >= 1 && val <= courses.size()) {
                        return courses.get(val - 1);
                    }
                    if (val == addOption) {
                        Course created = registerCourseFlow(true);
                        if (created != null) {
                            return created;
                        }
                    } else {
                        System.out.println("Invalid course selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric input.");
                }
            }
        }
    }

    private static Enrollment selectEnrollmentWithAdd() throws DuplicateRecordException {
        while (true) {
            List<Enrollment> activeList = new ArrayList<>();
            for (Enrollment e : enrollments) {
                if (e.isActive()) activeList.add(e);
            }

            System.out.println("+----------------- CHOOSE ACTIVE ENROLLMENT -----------------+");
            if (activeList.isEmpty()) {
                System.out.println("| No active enrollment exists.                               |");
                System.out.println("| 1 [+] Enroll a Student now                                 |");
                System.out.println("| 0 Cancel                                                   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                if (input.equals("1")) {
                    Enrollment created = enrollStudentFlow(true);
                    if (created != null) {
                        return created;
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                for (int i = 0; i < activeList.size(); i++) {
                    Enrollment e = activeList.get(i);
                    String line = String.format("| %d [%s] %s (%s)", (i + 1), e.getId(), e.getDisplayName(), e.getSemester());
                    if (line.length() > 60) line = line.substring(0, 58) + "..";
                    while (line.length() < 61) line += " ";
                    line += "|";
                    System.out.println(line);
                }
                int addOption = activeList.size() + 1;
                String addLine = String.format("| %d [+] Enroll a Student now", addOption);
                while (addLine.length() < 61) addLine += " ";
                addLine += "|";
                System.out.println(addLine);
                System.out.println("| 0 Cancel                                                   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                try {
                    int val = Integer.parseInt(input);
                    if (val >= 1 && val <= activeList.size()) {
                        return activeList.get(val - 1);
                    }
                    if (val == addOption) {
                        Enrollment created = enrollStudentFlow(true);
                        if (created != null) {
                            return created;
                        }
                    } else {
                        System.out.println("Invalid enrollment selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric input.");
                }
            }
        }
    }

    private static LibraryBook selectLibraryBookWithAdd(boolean availableOnly) throws DuplicateRecordException {
        while (true) {
            List<LibraryBook> candidates = new ArrayList<>();
            for (LibraryBook b : books) {
                if (!availableOnly || b.getAvailableCopies() > 0) {
                    candidates.add(b);
                }
            }

            System.out.println("+----------------- CHOOSE LIBRARY BOOK ----------------------+");
            if (candidates.isEmpty()) {
                System.out.println("| No library book available.                                 |");
                System.out.println("| 1 [+] Register a new Library Book                          |");
                System.out.println("| 0 Cancel                                                   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                if (input.equals("1")) {
                    LibraryBook created = registerLibraryBookFlow(true);
                    if (created != null && (!availableOnly || created.getAvailableCopies() > 0)) {
                        return created;
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } else {
                for (int i = 0; i < candidates.size(); i++) {
                    LibraryBook b = candidates.get(i);
                    String line = String.format("| %d [%s] %s | Copies: %d/%d", (i + 1), b.getBookId(), b.getTitle(), b.getAvailableCopies(), b.getTotalCopies());
                    if (line.length() > 60) line = line.substring(0, 58) + "..";
                    while (line.length() < 61) line += " ";
                    line += "|";
                    System.out.println(line);
                }
                int addOption = candidates.size() + 1;
                String addLine = String.format("| %d [+] Register a new Library Book", addOption);
                while (addLine.length() < 61) addLine += " ";
                addLine += "|";
                System.out.println(addLine);
                System.out.println("| 0 Cancel                                                   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("CANCEL") || input.equalsIgnoreCase("BACK") || input.equals("0")) {
                    return null;
                }
                try {
                    int val = Integer.parseInt(input);
                    if (val >= 1 && val <= candidates.size()) {
                        return candidates.get(val - 1);
                    }
                    if (val == addOption) {
                        LibraryBook created = registerLibraryBookFlow(true);
                        if (created != null && (!availableOnly || created.getAvailableCopies() > 0)) {
                            return created;
                        }
                    } else {
                        System.out.println("Invalid library book selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid numeric input.");
                }
            }
        }
    }

    private static Student registerStudentFlow(boolean nested) throws DuplicateRecordException {
        String personId = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        String phone = "";
        LocalDate dob = null;

        String memberNumber = "";
        Department department = null;
        LocalDate joinDate = null;
        String academicStatus = "ACTIVE";
        String institutionalEmail = "";
        String advisorName = "";

        String studentNumber = "";
        String programName = "";
        LocalDate admissionDate = null;
        int studyYear = 1;
        String guardianPhone = "";
        boolean scholarship = false;

        int step = 1;

        while (step >= 1 && step <= 4) {
            if (step == 1) {
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
                System.out.println("| STUDENT REGISTRATION                         Step 1 of 4   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| [1] Personal details         [2] Academic details          |");
                System.out.println("| [3] Student details          [4] Review & confirm          |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| Enter the requested value. Type BACK to return;            |");
                System.out.println("| CANCEL to discard the unsaved student.                     |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("[STEP 1/4] PERSONAL DETAILS");

                while (true) {
                    String p = personId.isEmpty() ? "" : " [" + personId + "]";
                    System.out.print("01 Person ID [String | unique | 4-30 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) return null;
                    if (in.isEmpty() && !personId.isEmpty()) break;
                    if (in.length() < Identifiable.MIN_ID_LENGTH || in.length() > Identifiable.MAX_ID_LENGTH) {
                        System.out.println("! Person ID must be " + Identifiable.MIN_ID_LENGTH + "-" + Identifiable.MAX_ID_LENGTH + " characters.");
                        continue;
                    }
                    if (!isIdUnique(in, null)) {
                        System.out.println("! Person ID " + in + " already exists. Please enter a different ID: ");
                        continue;
                    }
                    personId = in;
                    break;
                }

                while (true) {
                    String p = firstName.isEmpty() ? "" : " [" + firstName + "]";
                    System.out.print("02 First name [String | 2-60 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        personId = "";
                        break;
                    }
                    if (in.isEmpty() && !firstName.isEmpty()) break;
                    if (in.length() < Registrable.MIN_NAME_LENGTH || in.length() > Registrable.MAX_NAME_LENGTH) {
                        System.out.println("! First name must be " + Registrable.MIN_NAME_LENGTH + "-" + Registrable.MAX_NAME_LENGTH + " characters.");
                        continue;
                    }
                    firstName = in;
                    break;
                }
                if (personId.isEmpty()) continue;

                while (true) {
                    String p = lastName.isEmpty() ? "" : " [" + lastName + "]";
                    System.out.print("03 Last name [String | 2-60 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        firstName = "";
                        break;
                    }
                    if (in.isEmpty() && !lastName.isEmpty()) break;
                    if (in.length() < Registrable.MIN_NAME_LENGTH || in.length() > Registrable.MAX_NAME_LENGTH) {
                        System.out.println("! Last name must be " + Registrable.MIN_NAME_LENGTH + "-" + Registrable.MAX_NAME_LENGTH + " characters.");
                        continue;
                    }
                    lastName = in;
                    break;
                }
                if (firstName.isEmpty()) continue;

                while (true) {
                    String p = email.isEmpty() ? "" : " [" + email + "]";
                    System.out.print("04 Email [String | unique | email]" + p + ": ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        lastName = "";
                        break;
                    }
                    if (in.isEmpty() && !email.isEmpty()) break;
                    if (in.length() > Registrable.MAX_EMAIL_LENGTH || !in.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("! Enter a valid email address (example: name@example.com).");
                        continue;
                    }
                    if (!isEmailUnique(in, null)) {
                        System.out.println("! Email " + in + " already exists. Please enter a different email: ");
                        continue;
                    }
                    email = in;
                    break;
                }
                if (lastName.isEmpty()) continue;

                while (true) {
                    String p = phone.isEmpty() ? "" : " [" + phone + "]";
                    System.out.print("05 Phone [String | 10-15 digits]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        email = "";
                        break;
                    }
                    if (in.isEmpty() && !phone.isEmpty()) break;
                    if (!in.matches("^\\+?\\d{10,15}$")) {
                        System.out.println("! Phone number must contain 10-15 digits, optional leading +.");
                        continue;
                    }
                    phone = in;
                    break;
                }
                if (email.isEmpty()) continue;

                while (true) {
                    String p = dob == null ? "" : " [" + dob + "]";
                    System.out.print("06 Date of birth [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        phone = "";
                        break;
                    }
                    if (in.isEmpty() && dob != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (!d.isBefore(LocalDate.now())) {
                            System.out.println("! Date of birth must be in the past.");
                            continue;
                        }
                        dob = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date: use YYYY-MM-DD (example: 2004-05-16).");
                    }
                }
                if (phone.isEmpty()) continue;

                step = 2;

            } else if (step == 2) {
                System.out.println("\n[STEP 2/4] ACADEMIC DETAILS");

                while (true) {
                    String p = memberNumber.isEmpty() ? "" : " [" + memberNumber + "]";
                    System.out.print("07 Member number [String | unique | 4-30]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        step = 1;
                        break;
                    }
                    if (in.isEmpty() && !memberNumber.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30) {
                        System.out.println("! Member number must be 4-30 characters.");
                        continue;
                    }
                    if (!isMemberNumberUnique(in, null)) {
                        System.out.println("! Member number " + in + " already exists. Please enter a different member number: ");
                        continue;
                    }
                    memberNumber = in;
                    break;
                }
                if (step == 1) continue;

                if (department == null) {
                    department = selectDepartmentWithAdd();
                    if (department == null) {
                        memberNumber = "";
                        continue;
                    }
                } else {
                    System.out.println("08 Department: [" + department.getDepartmentCode() + "] " + department.getDepartmentName());
                    System.out.print("Change department? (Y/N) [N]: ");
                    String ch = scanner.nextLine().trim();
                    if (ch.equalsIgnoreCase("CANCEL")) return null;
                    if (ch.equalsIgnoreCase("BACK")) {
                        memberNumber = "";
                        continue;
                    }
                    if (ch.equalsIgnoreCase("Y")) {
                        Department nd = selectDepartmentWithAdd();
                        if (nd != null) department = nd;
                    }
                }

                while (true) {
                    String p = joinDate == null ? "" : " [" + joinDate + "]";
                    System.out.print("09 Join date [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        department = null;
                        break;
                    }
                    if (in.isEmpty() && joinDate != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (d.isAfter(LocalDate.now())) {
                            System.out.println("! Join date cannot be in the future.");
                            continue;
                        }
                        joinDate = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date: use YYYY-MM-DD (example: 2026-09-01).");
                    }
                }
                if (department == null) continue;

                while (true) {
                    System.out.print("10 Academic status [1 ACTIVE / 2 INACTIVE] [" + academicStatus + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        joinDate = null;
                        break;
                    }
                    if (in.isEmpty()) break;
                    if (in.equals("1") || in.equalsIgnoreCase("ACTIVE")) {
                        academicStatus = "ACTIVE";
                        break;
                    }
                    if (in.equals("2") || in.equalsIgnoreCase("INACTIVE")) {
                        academicStatus = "INACTIVE";
                        break;
                    }
                    System.out.println("! Enter 1 for ACTIVE or 2 for INACTIVE.");
                }
                if (joinDate == null) continue;

                while (true) {
                    String p = institutionalEmail.isEmpty() ? "" : " [" + institutionalEmail + "]";
                    System.out.print("11 Institutional email[String | unique | email]" + p + ": ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        academicStatus = "ACTIVE";
                        break;
                    }
                    if (in.isEmpty() && !institutionalEmail.isEmpty()) break;
                    if (in.length() > Registrable.MAX_EMAIL_LENGTH || !in.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("! Enter a valid email address (example: name@auca.ac.rw).");
                        continue;
                    }
                    if (!isInstEmailUnique(in, null)) {
                        System.out.println("! Institutional email " + in + " already exists.");
                        continue;
                    }
                    institutionalEmail = in;
                    break;
                }
                if (institutionalEmail.isEmpty()) continue;

                while (true) {
                    String p = advisorName.isEmpty() ? "" : " [" + advisorName + "]";
                    System.out.print("12 Advisor name [String | 2-120 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        institutionalEmail = "";
                        break;
                    }
                    if (in.isEmpty() && !advisorName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 120) {
                        System.out.println("! Advisor name must be 2-120 characters.");
                        continue;
                    }
                    advisorName = in;
                    break;
                }
                if (institutionalEmail.isEmpty()) continue;

                step = 3;

            } else if (step == 3) {
                System.out.println("\n[STEP 3/4] STUDENT DETAILS");

                while (true) {
                    String p = studentNumber.isEmpty() ? "" : " [" + studentNumber + "]";
                    System.out.print("13 Student number [String | unique | 4-30]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        step = 2;
                        break;
                    }
                    if (in.isEmpty() && !studentNumber.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30) {
                        System.out.println("! Student number must be 4-30 characters.");
                        continue;
                    }
                    if (!isStudentNumberUnique(in, null)) {
                        System.out.println("! Student number " + in + " already exists. Please enter a different student number: ");
                        continue;
                    }
                    studentNumber = in;
                    break;
                }
                if (step == 2) continue;

                while (true) {
                    String p = programName.isEmpty() ? "" : " [" + programName + "]";
                    System.out.print("14 Program name [String | 2-120 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        studentNumber = "";
                        break;
                    }
                    if (in.isEmpty() && !programName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 120) {
                        System.out.println("! Program name must be 2-120 characters.");
                        continue;
                    }
                    programName = in;
                    break;
                }
                if (studentNumber.isEmpty()) continue;

                while (true) {
                    String p = admissionDate == null ? "" : " [" + admissionDate + "]";
                    System.out.print("15 Admission date [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        programName = "";
                        break;
                    }
                    if (in.isEmpty() && admissionDate != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (d.isAfter(LocalDate.now())) {
                            System.out.println("! Admission date cannot be in the future.");
                            continue;
                        }
                        admissionDate = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date: use YYYY-MM-DD (example: 2026-09-01).");
                    }
                }
                if (programName.isEmpty()) continue;

                while (true) {
                    System.out.print("16 Study year [int | 1-6] [" + studyYear + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        admissionDate = null;
                        break;
                    }
                    if (in.isEmpty()) break;
                    try {
                        int y = Integer.parseInt(in);
                        if (y < 1 || y > 6) {
                            System.out.println("! Study year must be between 1 and 6.");
                            continue;
                        }
                        studyYear = y;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("! Please enter a whole number between 1 and 6.");
                    }
                }
                if (admissionDate == null) continue;

                while (true) {
                    String p = guardianPhone.isEmpty() ? "" : " [" + guardianPhone + "]";
                    System.out.print("17 Guardian phone [String | 10-15 digits]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        studyYear = 1;
                        break;
                    }
                    if (in.isEmpty() && !guardianPhone.isEmpty()) break;
                    if (!in.matches("^\\+?\\d{10,15}$")) {
                        System.out.println("! Guardian phone must contain 10-15 digits, optional leading +.");
                        continue;
                    }
                    guardianPhone = in;
                    break;
                }
                if (guardianPhone.isEmpty()) continue;

                while (true) {
                    System.out.print("18 Scholarship [1 YES / 2 NO] [" + (scholarship ? "YES" : "NO") + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) {
                        guardianPhone = "";
                        break;
                    }
                    if (in.isEmpty()) break;
                    if (in.equals("1") || in.equalsIgnoreCase("YES") || in.equalsIgnoreCase("Y")) {
                        scholarship = true;
                        break;
                    }
                    if (in.equals("2") || in.equalsIgnoreCase("NO") || in.equalsIgnoreCase("N")) {
                        scholarship = false;
                        break;
                    }
                    System.out.println("! Enter 1 for YES or 2 for NO.");
                }
                if (guardianPhone.isEmpty()) continue;

                step = 4;

            } else if (step == 4) {
                System.out.println("\n+---------------- REVIEW STUDENT ---------------------------+");
                System.out.println("| PERSON                                                     |");
                System.out.printf("| 01 Person ID          : %-34s |\n", personId);
                System.out.printf("| 02 First name         : %-34s |\n", firstName);
                System.out.printf("| 03 Last name          : %-34s |\n", lastName);
                System.out.printf("| 04 Email              : %-34s |\n", email);
                System.out.printf("| 05 Phone              : %-34s |\n", phone);
                System.out.printf("| 06 Date of birth      : %-34s |\n", dob);
                System.out.println("| ACADEMIC MEMBER                                            |");
                System.out.printf("| 07 Member number      : %-34s |\n", memberNumber);
                System.out.printf("| 08 Department         : %-34s |\n", "[" + department.getDepartmentCode() + "] " + department.getDepartmentName());
                System.out.printf("| 09 Join date          : %-34s |\n", joinDate);
                System.out.printf("| 10 Academic status    : %-34s |\n", academicStatus);
                System.out.printf("| 11 Institutional email: %-34s |\n", institutionalEmail);
                System.out.printf("| 12 Advisor name       : %-34s |\n", advisorName);
                System.out.println("| STUDENT                                                    |");
                System.out.printf("| 13 Student number     : %-34s |\n", studentNumber);
                System.out.printf("| 14 Program name       : %-34s |\n", programName);
                System.out.printf("| 15 Admission date     : %-34s |\n", admissionDate);
                System.out.printf("| 16 Study year         : %-34s |\n", studyYear);
                System.out.printf("| 17 Guardian phone     : %-34s |\n", guardianPhone);
                System.out.printf("| 18 Scholarship        : %-34s |\n", (scholarship ? "YES" : "NO"));
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| [1] Save student      [2] Edit field        [0] Cancel     |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String opt = scanner.nextLine().trim();

                if (opt.equals("0") || opt.equalsIgnoreCase("CANCEL")) {
                    System.out.println("Registration cancelled. Student not saved.");
                    return null;
                }
                if (opt.equalsIgnoreCase("BACK")) {
                    step = 3;
                    continue;
                }
                if (opt.equals("2")) {
                    System.out.print("Enter field number to edit (01-18) or 0 to return: ");
                    String fStr = scanner.nextLine().trim();
                    try {
                        int fNum = Integer.parseInt(fStr);
                        switch (fNum) {
                            case 1:
                                System.out.println("Current Person ID: " + personId);
                                System.out.print("New Person ID: ");
                                String np = scanner.nextLine().trim();
                                if (np.length() >= 4 && np.length() <= 30 && isIdUnique(np, null)) personId = np;
                                else System.out.println("! Invalid or duplicate Person ID.");
                                break;
                            case 2:
                                System.out.println("Current First name: " + firstName);
                                System.out.print("New First name: ");
                                String nf = scanner.nextLine().trim();
                                if (nf.length() >= 2 && nf.length() <= 60) firstName = nf;
                                else System.out.println("! Must be 2-60 characters.");
                                break;
                            case 3:
                                System.out.println("Current Last name: " + lastName);
                                System.out.print("New Last name: ");
                                String nl = scanner.nextLine().trim();
                                if (nl.length() >= 2 && nl.length() <= 60) lastName = nl;
                                else System.out.println("! Must be 2-60 characters.");
                                break;
                            case 4:
                                System.out.println("Current Email: " + email);
                                System.out.print("New Email: ");
                                String ne = scanner.nextLine().trim();
                                if (ne.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") && isEmailUnique(ne, null)) email = ne;
                                else System.out.println("! Invalid or duplicate email.");
                                break;
                            case 5:
                                System.out.println("Current Phone: " + phone);
                                System.out.print("New Phone: ");
                                String nph = scanner.nextLine().trim();
                                if (nph.matches("^\\+?\\d{10,15}$")) phone = nph;
                                else System.out.println("! Invalid phone number.");
                                break;
                            case 6:
                                System.out.println("Current Date of birth: " + dob);
                                System.out.print("New Date of birth (YYYY-MM-DD): ");
                                try {
                                    LocalDate nd = LocalDate.parse(scanner.nextLine().trim());
                                    if (nd.isBefore(LocalDate.now())) dob = nd;
                                    else System.out.println("! Date of birth must be in the past.");
                                } catch (Exception e) {
                                    System.out.println("! Invalid date format.");
                                }
                                break;
                            case 7:
                                System.out.println("Current Member number: " + memberNumber);
                                System.out.print("New Member number: ");
                                String nm = scanner.nextLine().trim();
                                if (nm.length() >= 4 && nm.length() <= 30 && isMemberNumberUnique(nm, null)) memberNumber = nm;
                                else System.out.println("! Invalid or duplicate member number.");
                                break;
                            case 8:
                                Department ndept = selectDepartmentWithAdd();
                                if (ndept != null) department = ndept;
                                break;
                            case 9:
                                System.out.println("Current Join date: " + joinDate);
                                System.out.print("New Join date (YYYY-MM-DD): ");
                                try {
                                    LocalDate nd = LocalDate.parse(scanner.nextLine().trim());
                                    if (!nd.isAfter(LocalDate.now())) joinDate = nd;
                                    else System.out.println("! Join date cannot be in the future.");
                                } catch (Exception e) {
                                    System.out.println("! Invalid date format.");
                                }
                                break;
                            case 10:
                                System.out.println("Current Academic status: " + academicStatus);
                                System.out.print("New Academic status (1 ACTIVE / 2 INACTIVE): ");
                                String ns = scanner.nextLine().trim();
                                if (ns.equals("1") || ns.equalsIgnoreCase("ACTIVE")) academicStatus = "ACTIVE";
                                else if (ns.equals("2") || ns.equalsIgnoreCase("INACTIVE")) academicStatus = "INACTIVE";
                                break;
                            case 11:
                                System.out.println("Current Institutional email: " + institutionalEmail);
                                System.out.print("New Institutional email: ");
                                String nie = scanner.nextLine().trim();
                                if (nie.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") && isInstEmailUnique(nie, null)) institutionalEmail = nie;
                                else System.out.println("! Invalid or duplicate email.");
                                break;
                            case 12:
                                System.out.println("Current Advisor name: " + advisorName);
                                System.out.print("New Advisor name: ");
                                String na = scanner.nextLine().trim();
                                if (na.length() >= 2 && na.length() <= 120) advisorName = na;
                                else System.out.println("! Must be 2-120 characters.");
                                break;
                            case 13:
                                System.out.println("Current Student number: " + studentNumber);
                                System.out.print("New Student number: ");
                                String nsn = scanner.nextLine().trim();
                                if (nsn.length() >= 4 && nsn.length() <= 30 && isStudentNumberUnique(nsn, null)) studentNumber = nsn;
                                else System.out.println("! Invalid or duplicate student number.");
                                break;
                            case 14:
                                System.out.println("Current Program name: " + programName);
                                System.out.print("New Program name: ");
                                String npr = scanner.nextLine().trim();
                                if (npr.length() >= 2 && npr.length() <= 120) programName = npr;
                                else System.out.println("! Must be 2-120 characters.");
                                break;
                            case 15:
                                System.out.println("Current Admission date: " + admissionDate);
                                System.out.print("New Admission date (YYYY-MM-DD): ");
                                try {
                                    LocalDate nd = LocalDate.parse(scanner.nextLine().trim());
                                    if (!nd.isAfter(LocalDate.now())) admissionDate = nd;
                                    else System.out.println("! Cannot be in the future.");
                                } catch (Exception e) {
                                    System.out.println("! Invalid date format.");
                                }
                                break;
                            case 16:
                                System.out.println("Current Study year: " + studyYear);
                                System.out.print("New Study year (1-6): ");
                                try {
                                    int ny = Integer.parseInt(scanner.nextLine().trim());
                                    if (ny >= 1 && ny <= 6) studyYear = ny;
                                    else System.out.println("! Must be between 1 and 6.");
                                } catch (Exception e) {
                                    System.out.println("! Invalid number.");
                                }
                                break;
                            case 17:
                                System.out.println("Current Guardian phone: " + guardianPhone);
                                System.out.print("New Guardian phone: ");
                                String ngp = scanner.nextLine().trim();
                                if (ngp.matches("^\\+?\\d{10,15}$")) guardianPhone = ngp;
                                else System.out.println("! Invalid phone number.");
                                break;
                            case 18:
                                System.out.println("Current Scholarship: " + (scholarship ? "YES" : "NO"));
                                System.out.print("New Scholarship (1 YES / 2 NO): ");
                                String nsc = scanner.nextLine().trim();
                                if (nsc.equals("1") || nsc.equalsIgnoreCase("YES")) scholarship = true;
                                else if (nsc.equals("2") || nsc.equalsIgnoreCase("NO")) scholarship = false;
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("! Invalid field selection.");
                    }
                    continue;
                }

                if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
                    checkUniqueId(personId);
                    Student s = new Student(personId, dob, phone, email, lastName, firstName,
                            memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName,
                            studentNumber, scholarship, guardianPhone, studyYear, admissionDate, programName);
                    try {
                        s.validate();
                        students.add(s);
                        System.out.println("+ Student " + studentNumber + " registered successfully.");
                        if (nested) {
                            return s;
                        }
                        System.out.println(" [1] Register another   [2] View student   [0] Main menu");
                        System.out.print("Select: ");
                        String post = scanner.nextLine().trim();
                        if (post.equals("1")) {
                            return registerStudentFlow(false);
                        }
                        if (post.equals("2")) {
                            s.printDetails();
                        }
                        return s;
                    } catch (ValidationException e) {
                        System.out.println("! Validation failed: " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }

    private static Teacher registerTeacherFlow(boolean nested) throws DuplicateRecordException {
        String personId = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        String phone = "";
        LocalDate dob = null;

        String memberNumber = "";
        Department department = null;
        LocalDate joinDate = null;
        String academicStatus = "ACTIVE";
        String institutionalEmail = "";
        String advisorName = "";

        String employeeNumber = "";
        String specialization = "";
        LocalDate hireDate = null;
        BigDecimal salary = BigDecimal.ZERO;
        String officeNumber = "";
        String employmentType = "FULL-TIME";

        int step = 1;

        while (step >= 1 && step <= 4) {
            if (step == 1) {
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
                System.out.println("| TEACHER REGISTRATION                         Step 1 of 4   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| [1] Personal details         [2] Academic details          |");
                System.out.println("| [3] Teacher details          [4] Review & confirm          |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| Enter the requested value. Type BACK to return;            |");
                System.out.println("| CANCEL to discard the unsaved teacher.                     |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("[STEP 1/4] PERSONAL DETAILS");

                while (true) {
                    String p = personId.isEmpty() ? "" : " [" + personId + "]";
                    System.out.print("01 Person ID [String | unique | 4-30 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
                    if (in.isEmpty() && !personId.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30) {
                        System.out.println("! Person ID must be 4-30 characters.");
                        continue;
                    }
                    if (!isIdUnique(in, null)) {
                        System.out.println("! Person ID " + in + " already exists.");
                        continue;
                    }
                    personId = in;
                    break;
                }

                while (true) {
                    String p = firstName.isEmpty() ? "" : " [" + firstName + "]";
                    System.out.print("02 First name [String | 2-60 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { personId = ""; break; }
                    if (in.isEmpty() && !firstName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 60) {
                        System.out.println("! First name must be 2-60 characters.");
                        continue;
                    }
                    firstName = in;
                    break;
                }
                if (personId.isEmpty()) continue;

                while (true) {
                    String p = lastName.isEmpty() ? "" : " [" + lastName + "]";
                    System.out.print("03 Last name [String | 2-60 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { firstName = ""; break; }
                    if (in.isEmpty() && !lastName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 60) {
                        System.out.println("! Last name must be 2-60 characters.");
                        continue;
                    }
                    lastName = in;
                    break;
                }
                if (firstName.isEmpty()) continue;

                while (true) {
                    String p = email.isEmpty() ? "" : " [" + email + "]";
                    System.out.print("04 Email [String | unique | email]" + p + ": ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { lastName = ""; break; }
                    if (in.isEmpty() && !email.isEmpty()) break;
                    if (in.length() > 100 || !in.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("! Enter a valid email address.");
                        continue;
                    }
                    if (!isEmailUnique(in, null)) {
                        System.out.println("! Email " + in + " already exists.");
                        continue;
                    }
                    email = in;
                    break;
                }
                if (lastName.isEmpty()) continue;

                while (true) {
                    String p = phone.isEmpty() ? "" : " [" + phone + "]";
                    System.out.print("05 Phone [String | 10-15 digits]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { email = ""; break; }
                    if (in.isEmpty() && !phone.isEmpty()) break;
                    if (!in.matches("^\\+?\\d{10,15}$")) {
                        System.out.println("! Phone number must contain 10-15 digits.");
                        continue;
                    }
                    phone = in;
                    break;
                }
                if (email.isEmpty()) continue;

                while (true) {
                    String p = dob == null ? "" : " [" + dob + "]";
                    System.out.print("06 Date of birth [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { phone = ""; break; }
                    if (in.isEmpty() && dob != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (!d.isBefore(LocalDate.now())) {
                            System.out.println("! Date of birth must be in the past.");
                            continue;
                        }
                        dob = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date: use YYYY-MM-DD.");
                    }
                }
                if (phone.isEmpty()) continue;

                step = 2;

            } else if (step == 2) {
                System.out.println("\n[STEP 2/4] ACADEMIC DETAILS");

                while (true) {
                    String p = memberNumber.isEmpty() ? "" : " [" + memberNumber + "]";
                    System.out.print("07 Member number [String | unique | 4-30]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { step = 1; break; }
                    if (in.isEmpty() && !memberNumber.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30) {
                        System.out.println("! Member number must be 4-30 characters.");
                        continue;
                    }
                    if (!isMemberNumberUnique(in, null)) {
                        System.out.println("! Member number " + in + " already exists.");
                        continue;
                    }
                    memberNumber = in;
                    break;
                }
                if (step == 1) continue;

                if (department == null) {
                    department = selectDepartmentWithAdd();
                    if (department == null) { memberNumber = ""; continue; }
                } else {
                    System.out.println("08 Department: [" + department.getDepartmentCode() + "] " + department.getDepartmentName());
                    System.out.print("Change department? (Y/N) [N]: ");
                    String ch = scanner.nextLine().trim();
                    if (ch.equalsIgnoreCase("CANCEL")) return null;
                    if (ch.equalsIgnoreCase("BACK")) { memberNumber = ""; continue; }
                    if (ch.equalsIgnoreCase("Y")) {
                        Department nd = selectDepartmentWithAdd();
                        if (nd != null) department = nd;
                    }
                }

                while (true) {
                    String p = joinDate == null ? "" : " [" + joinDate + "]";
                    System.out.print("09 Join date [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { department = null; break; }
                    if (in.isEmpty() && joinDate != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (d.isAfter(LocalDate.now())) {
                            System.out.println("! Join date cannot be in the future.");
                            continue;
                        }
                        joinDate = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date format.");
                    }
                }
                if (department == null) continue;

                while (true) {
                    System.out.print("10 Academic status [1 ACTIVE / 2 INACTIVE] [" + academicStatus + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { joinDate = null; break; }
                    if (in.isEmpty()) break;
                    if (in.equals("1") || in.equalsIgnoreCase("ACTIVE")) { academicStatus = "ACTIVE"; break; }
                    if (in.equals("2") || in.equalsIgnoreCase("INACTIVE")) { academicStatus = "INACTIVE"; break; }
                    System.out.println("! Enter 1 for ACTIVE or 2 for INACTIVE.");
                }
                if (joinDate == null) continue;

                while (true) {
                    String p = institutionalEmail.isEmpty() ? "" : " [" + institutionalEmail + "]";
                    System.out.print("11 Institutional email[String | unique | email]" + p + ": ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { academicStatus = "ACTIVE"; break; }
                    if (in.isEmpty() && !institutionalEmail.isEmpty()) break;
                    if (in.length() > 100 || !in.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("! Enter a valid institutional email.");
                        continue;
                    }
                    if (!isInstEmailUnique(in, null)) {
                        System.out.println("! Institutional email " + in + " already exists.");
                        continue;
                    }
                    institutionalEmail = in;
                    break;
                }
                if (institutionalEmail.isEmpty()) continue;

                while (true) {
                    String p = advisorName.isEmpty() ? "" : " [" + advisorName + "]";
                    System.out.print("12 Advisor name [String | 2-120 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { institutionalEmail = ""; break; }
                    if (in.isEmpty() && !advisorName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 120) {
                        System.out.println("! Advisor name must be 2-120 characters.");
                        continue;
                    }
                    advisorName = in;
                    break;
                }
                if (institutionalEmail.isEmpty()) continue;

                step = 3;

            } else if (step == 3) {
                System.out.println("\n[STEP 3/4] TEACHER DETAILS");

                while (true) {
                    String p = employeeNumber.isEmpty() ? "" : " [" + employeeNumber + "]";
                    System.out.print("13 Employee number [String | unique | 4-30]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { step = 2; break; }
                    if (in.isEmpty() && !employeeNumber.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30) {
                        System.out.println("! Employee number must be 4-30 characters.");
                        continue;
                    }
                    if (!isEmployeeNumberUnique(in, null)) {
                        System.out.println("! Employee number " + in + " already exists.");
                        continue;
                    }
                    employeeNumber = in;
                    break;
                }
                if (step == 2) continue;

                while (true) {
                    String p = specialization.isEmpty() ? "" : " [" + specialization + "]";
                    System.out.print("14 Specialization [String | 2-120 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { employeeNumber = ""; break; }
                    if (in.isEmpty() && !specialization.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 120) {
                        System.out.println("! Specialization must be 2-120 characters.");
                        continue;
                    }
                    specialization = in;
                    break;
                }
                if (employeeNumber.isEmpty()) continue;

                while (true) {
                    String p = hireDate == null ? "" : " [" + hireDate + "]";
                    System.out.print("15 Hire date [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { specialization = ""; break; }
                    if (in.isEmpty() && hireDate != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (d.isAfter(LocalDate.now())) {
                            System.out.println("! Hire date cannot be in the future.");
                            continue;
                        }
                        hireDate = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date format.");
                    }
                }
                if (specialization.isEmpty()) continue;

                while (true) {
                    String p = salary.compareTo(BigDecimal.ZERO) > 0 ? " [" + salary + "]" : "";
                    System.out.print("16 Salary [BigDecimal | > 0]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { hireDate = null; break; }
                    if (in.isEmpty() && salary.compareTo(BigDecimal.ZERO) > 0) break;
                    try {
                        BigDecimal sal = new BigDecimal(in);
                        if (sal.compareTo(BigDecimal.ZERO) <= 0) {
                            System.out.println("! Salary must be strictly positive.");
                            continue;
                        }
                        salary = sal;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("! Enter a valid monetary amount.");
                    }
                }
                if (hireDate == null) continue;

                while (true) {
                    String p = officeNumber.isEmpty() ? "" : " [" + officeNumber + "]";
                    System.out.print("17 Office number [String | nonblank]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { salary = BigDecimal.ZERO; break; }
                    if (in.isEmpty() && !officeNumber.isEmpty()) break;
                    if (in.isEmpty()) {
                        System.out.println("! Office number is required.");
                        continue;
                    }
                    officeNumber = in;
                    break;
                }
                if (salary.compareTo(BigDecimal.ZERO) <= 0) continue;

                while (true) {
                    System.out.print("18 Employment type [1 FULL-TIME / 2 PART-TIME / 3 CONTRACT] [" + employmentType + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { officeNumber = ""; break; }
                    if (in.isEmpty()) break;
                    if (in.equals("1") || in.equalsIgnoreCase("FULL-TIME")) { employmentType = "FULL-TIME"; break; }
                    if (in.equals("2") || in.equalsIgnoreCase("PART-TIME")) { employmentType = "PART-TIME"; break; }
                    if (in.equals("3") || in.equalsIgnoreCase("CONTRACT")) { employmentType = "CONTRACT"; break; }
                    employmentType = in;
                    break;
                }
                if (officeNumber.isEmpty()) continue;

                step = 4;

            } else if (step == 4) {
                System.out.println("\n+---------------- REVIEW TEACHER ---------------------------+");
                System.out.println("| PERSON                                                     |");
                System.out.printf("| 01 Person ID          : %-34s |\n", personId);
                System.out.printf("| 02 First name         : %-34s |\n", firstName);
                System.out.printf("| 03 Last name          : %-34s |\n", lastName);
                System.out.printf("| 04 Email              : %-34s |\n", email);
                System.out.printf("| 05 Phone              : %-34s |\n", phone);
                System.out.printf("| 06 Date of birth      : %-34s |\n", dob);
                System.out.println("| ACADEMIC MEMBER                                            |");
                System.out.printf("| 07 Member number      : %-34s |\n", memberNumber);
                System.out.printf("| 08 Department         : %-34s |\n", "[" + department.getDepartmentCode() + "] " + department.getDepartmentName());
                System.out.printf("| 09 Join date          : %-34s |\n", joinDate);
                System.out.printf("| 10 Academic status    : %-34s |\n", academicStatus);
                System.out.printf("| 11 Institutional email: %-34s |\n", institutionalEmail);
                System.out.printf("| 12 Advisor name       : %-34s |\n", advisorName);
                System.out.println("| TEACHER                                                    |");
                System.out.printf("| 13 Employee number    : %-34s |\n", employeeNumber);
                System.out.printf("| 14 Specialization     : %-34s |\n", specialization);
                System.out.printf("| 15 Hire date          : %-34s |\n", hireDate);
                System.out.printf("| 16 Salary             : %-34s |\n", salary);
                System.out.printf("| 17 Office number      : %-34s |\n", officeNumber);
                System.out.printf("| 18 Employment type    : %-34s |\n", employmentType);
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| [1] Save teacher      [2] Edit field        [0] Cancel     |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String opt = scanner.nextLine().trim();

                if (opt.equals("0") || opt.equalsIgnoreCase("CANCEL")) {
                    System.out.println("Registration cancelled. Teacher not saved.");
                    return null;
                }
                if (opt.equalsIgnoreCase("BACK")) {
                    step = 3;
                    continue;
                }
                if (opt.equals("2")) {
                    System.out.print("Enter field number to edit (01-18) or 0 to return: ");
                    try {
                        int fNum = Integer.parseInt(scanner.nextLine().trim());
                        if (fNum == 8) {
                            Department nd = selectDepartmentWithAdd();
                            if (nd != null) department = nd;
                        } else if (fNum == 16) {
                            System.out.print("New Salary: ");
                            BigDecimal nsal = new BigDecimal(scanner.nextLine().trim());
                            if (nsal.compareTo(BigDecimal.ZERO) > 0) salary = nsal;
                        } else if (fNum == 14) {
                            System.out.print("New Specialization: ");
                            String ns = scanner.nextLine().trim();
                            if (ns.length() >= 2 && ns.length() <= 120) specialization = ns;
                        } else if (fNum == 17) {
                            System.out.print("New Office number: ");
                            String no = scanner.nextLine().trim();
                            if (!no.isEmpty()) officeNumber = no;
                        }
                    } catch (Exception e) {
                        System.out.println("! Invalid field selection.");
                    }
                    continue;
                }

                if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
                    checkUniqueId(personId);
                    Teacher t = new Teacher(personId, dob, phone, email, lastName, firstName,
                            memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName,
                            employeeNumber, specialization, hireDate, salary, officeNumber, employmentType);
                    try {
                        t.validate();
                        teachers.add(t);
                        System.out.println("+ Teacher " + employeeNumber + " registered successfully.");
                        if (nested) {
                            return t;
                        }
                        System.out.println(" [1] Register another   [2] View teacher   [0] Main menu");
                        System.out.print("Select: ");
                        String post = scanner.nextLine().trim();
                        if (post.equals("1")) {
                            return registerTeacherFlow(false);
                        }
                        if (post.equals("2")) {
                            t.printDetails();
                        }
                        return t;
                    } catch (ValidationException e) {
                        System.out.println("! Validation failed: " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }

    private static Administrator registerAdministratorFlow(boolean nested) throws DuplicateRecordException {
        String personId = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        String phone = "";
        LocalDate dob = null;

        String staffNumber = "";
        String jobTitle = "";
        String assignedOffice = "";
        LocalDate employmentDate = null;
        int accessLevel = 1;
        boolean active = true;

        int step = 1;

        while (step >= 1 && step <= 3) {
            if (step == 1) {
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
                System.out.println("| ADMINISTRATOR REGISTRATION                   Step 1 of 3   |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| [1] Personal details         [2] Administrator details     |");
                System.out.println("| [3] Review & confirm                                       |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| Enter the requested value. Type BACK to return;            |");
                System.out.println("| CANCEL to discard the unsaved administrator.               |");
                System.out.println("+------------------------------------------------------------+");
                System.out.println("[STEP 1/3] PERSONAL DETAILS");

                while (true) {
                    String p = personId.isEmpty() ? "" : " [" + personId + "]";
                    System.out.print("01 Person ID [String | unique | 4-30 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
                    if (in.isEmpty() && !personId.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30) {
                        System.out.println("! Person ID must be 4-30 characters.");
                        continue;
                    }
                    if (!isIdUnique(in, null)) {
                        System.out.println("! Person ID already exists.");
                        continue;
                    }
                    personId = in;
                    break;
                }

                while (true) {
                    String p = firstName.isEmpty() ? "" : " [" + firstName + "]";
                    System.out.print("02 First name [String | 2-60 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { personId = ""; break; }
                    if (in.isEmpty() && !firstName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 60) {
                        System.out.println("! Must be 2-60 characters.");
                        continue;
                    }
                    firstName = in;
                    break;
                }
                if (personId.isEmpty()) continue;

                while (true) {
                    String p = lastName.isEmpty() ? "" : " [" + lastName + "]";
                    System.out.print("03 Last name [String | 2-60 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { firstName = ""; break; }
                    if (in.isEmpty() && !lastName.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 60) {
                        System.out.println("! Must be 2-60 characters.");
                        continue;
                    }
                    lastName = in;
                    break;
                }
                if (firstName.isEmpty()) continue;

                while (true) {
                    String p = email.isEmpty() ? "" : " [" + email + "]";
                    System.out.print("04 Email [String | unique | email]" + p + ": ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { lastName = ""; break; }
                    if (in.isEmpty() && !email.isEmpty()) break;
                    if (!in.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") || !isEmailUnique(in, null)) {
                        System.out.println("! Invalid or duplicate email.");
                        continue;
                    }
                    email = in;
                    break;
                }
                if (lastName.isEmpty()) continue;

                while (true) {
                    String p = phone.isEmpty() ? "" : " [" + phone + "]";
                    System.out.print("05 Phone [String | 10-15 digits]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { email = ""; break; }
                    if (in.isEmpty() && !phone.isEmpty()) break;
                    if (!in.matches("^\\+?\\d{10,15}$")) {
                        System.out.println("! Must be 10-15 digits.");
                        continue;
                    }
                    phone = in;
                    break;
                }
                if (email.isEmpty()) continue;

                while (true) {
                    String p = dob == null ? "" : " [" + dob + "]";
                    System.out.print("06 Date of birth [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { phone = ""; break; }
                    if (in.isEmpty() && dob != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (!d.isBefore(LocalDate.now())) {
                            System.out.println("! Date of birth must be in the past.");
                            continue;
                        }
                        dob = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date format.");
                    }
                }
                if (phone.isEmpty()) continue;

                step = 2;

            } else if (step == 2) {
                System.out.println("\n[STEP 2/3] ADMINISTRATOR DETAILS");

                while (true) {
                    String p = staffNumber.isEmpty() ? "" : " [" + staffNumber + "]";
                    System.out.print("07 Staff number [String | unique | 4-30]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { step = 1; break; }
                    if (in.isEmpty() && !staffNumber.isEmpty()) break;
                    if (in.length() < 4 || in.length() > 30 || !isStaffNumberUnique(in, null)) {
                        System.out.println("! Invalid or duplicate staff number.");
                        continue;
                    }
                    staffNumber = in;
                    break;
                }
                if (step == 1) continue;

                while (true) {
                    String p = jobTitle.isEmpty() ? "" : " [" + jobTitle + "]";
                    System.out.print("08 Job title [String | 2-120 chars]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { staffNumber = ""; break; }
                    if (in.isEmpty() && !jobTitle.isEmpty()) break;
                    if (in.length() < 2 || in.length() > 120) {
                        System.out.println("! Job title must be 2-120 characters.");
                        continue;
                    }
                    jobTitle = in;
                    break;
                }
                if (staffNumber.isEmpty()) continue;

                while (true) {
                    String p = assignedOffice.isEmpty() ? "" : " [" + assignedOffice + "]";
                    System.out.print("09 Assigned office [String | nonblank]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { jobTitle = ""; break; }
                    if (in.isEmpty() && !assignedOffice.isEmpty()) break;
                    if (in.isEmpty()) {
                        System.out.println("! Assigned office is required.");
                        continue;
                    }
                    assignedOffice = in;
                    break;
                }
                if (jobTitle.isEmpty()) continue;

                while (true) {
                    String p = employmentDate == null ? "" : " [" + employmentDate + "]";
                    System.out.print("10 Employment date [LocalDate | YYYY-MM-DD]" + p + " : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { assignedOffice = ""; break; }
                    if (in.isEmpty() && employmentDate != null) break;
                    try {
                        LocalDate d = LocalDate.parse(in);
                        if (d.isAfter(LocalDate.now())) {
                            System.out.println("! Cannot be in the future.");
                            continue;
                        }
                        employmentDate = d;
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("! Invalid date format.");
                    }
                }
                if (assignedOffice.isEmpty()) continue;

                while (true) {
                    System.out.print("11 Access level [int | >= 1] [" + accessLevel + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { employmentDate = null; break; }
                    if (in.isEmpty()) break;
                    try {
                        int al = Integer.parseInt(in);
                        if (al < 1) {
                            System.out.println("! Access level must be at least 1.");
                            continue;
                        }
                        accessLevel = al;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("! Enter a valid integer.");
                    }
                }
                if (employmentDate == null) continue;

                while (true) {
                    System.out.print("12 Active status [1 YES / 2 NO] [" + (active ? "YES" : "NO") + "] : ");
                    String in = scanner.nextLine().trim();
                    if (in.equalsIgnoreCase("CANCEL")) return null;
                    if (in.equalsIgnoreCase("BACK")) { accessLevel = 1; break; }
                    if (in.isEmpty()) break;
                    if (in.equals("1") || in.equalsIgnoreCase("YES")) { active = true; break; }
                    if (in.equals("2") || in.equalsIgnoreCase("NO")) { active = false; break; }
                    System.out.println("! Enter 1 for YES or 2 for NO.");
                }
                if (accessLevel < 1) continue;

                step = 3;

            } else if (step == 3) {
                System.out.println("\n+---------------- REVIEW ADMINISTRATOR ---------------------+");
                System.out.println("| PERSON                                                     |");
                System.out.printf("| 01 Person ID          : %-34s |\n", personId);
                System.out.printf("| 02 First name         : %-34s |\n", firstName);
                System.out.printf("| 03 Last name          : %-34s |\n", lastName);
                System.out.printf("| 04 Email              : %-34s |\n", email);
                System.out.printf("| 05 Phone              : %-34s |\n", phone);
                System.out.printf("| 06 Date of birth      : %-34s |\n", dob);
                System.out.println("| ADMINISTRATOR                                              |");
                System.out.printf("| 07 Staff number       : %-34s |\n", staffNumber);
                System.out.printf("| 08 Job title          : %-34s |\n", jobTitle);
                System.out.printf("| 09 Assigned office    : %-34s |\n", assignedOffice);
                System.out.printf("| 10 Employment date    : %-34s |\n", employmentDate);
                System.out.printf("| 11 Access level       : %-34s |\n", accessLevel);
                System.out.printf("| 12 Active status      : %-34s |\n", (active ? "YES" : "NO"));
                System.out.println("+------------------------------------------------------------+");
                System.out.println("| [1] Save administrator [2] Edit field       [0] Cancel     |");
                System.out.println("+------------------------------------------------------------+");
                System.out.print("Select option: ");
                String opt = scanner.nextLine().trim();

                if (opt.equals("0") || opt.equalsIgnoreCase("CANCEL")) {
                    System.out.println("Registration cancelled. Administrator not saved.");
                    return null;
                }
                if (opt.equalsIgnoreCase("BACK")) {
                    step = 2;
                    continue;
                }
                if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
                    checkUniqueId(personId);
                    Administrator admin = new Administrator(personId, dob, phone, email, lastName, firstName,
                            staffNumber, jobTitle, assignedOffice, employmentDate, accessLevel, active);
                    try {
                        admin.validate();
                        administrators.add(admin);
                        System.out.println("+ Administrator " + staffNumber + " registered successfully.");
                        if (nested) return admin;
                        System.out.println(" [1] Register another   [2] View administrator   [0] Main menu");
                        System.out.print("Select: ");
                        String post = scanner.nextLine().trim();
                        if (post.equals("1")) return registerAdministratorFlow(false);
                        if (post.equals("2")) admin.printDetails();
                        return admin;
                    } catch (ValidationException e) {
                        System.out.println("! Validation failed: " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }

    private static Department registerDepartmentFlow(boolean nested) throws DuplicateRecordException {
        String id = "";
        String name = "";
        String code = "";
        String office = "";
        String email = "";
        Teacher head = null;

        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| DEPARTMENT REGISTRATION                      Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        while (true) {
            String p = id.isEmpty() ? "" : " [" + id + "]";
            System.out.print("01 Department ID [String | unique | 4-30 chars]" + p + " : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() && !id.isEmpty()) break;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Department ID.");
                continue;
            }
            id = in;
            break;
        }

        while (true) {
            String p = name.isEmpty() ? "" : " [" + name + "]";
            System.out.print("02 Department name [String | 2-60 chars]" + p + " : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() && !name.isEmpty()) break;
            if (in.length() < 2 || in.length() > 60) {
                System.out.println("! Department name must be 2-60 characters.");
                continue;
            }
            name = in;
            break;
        }

        while (true) {
            String p = code.isEmpty() ? "" : " [" + code + "]";
            System.out.print("03 Department code [String | unique]" + p + " : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() && !code.isEmpty()) break;
            if (in.isEmpty() || !isDepartmentCodeUnique(in, null)) {
                System.out.println("! Invalid or duplicate Department code.");
                continue;
            }
            code = in;
            break;
        }

        while (true) {
            String p = office.isEmpty() ? "" : " [" + office + "]";
            System.out.print("04 Office location [String | nonblank]" + p + " : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() && !office.isEmpty()) break;
            if (in.isEmpty()) {
                System.out.println("! Office location is required.");
                continue;
            }
            office = in;
            break;
        }

        while (true) {
            String p = email.isEmpty() ? "" : " [" + email + "]";
            System.out.print("05 Contact email [String | email]" + p + " : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() && !email.isEmpty()) break;
            if (!in.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("! Enter a valid department email.");
                continue;
            }
            email = in;
            break;
        }

        if (!teachers.isEmpty()) {
            System.out.println("06 Head Teacher (optional):");
            System.out.println("0. None / Skip");
            for (int i = 0; i < teachers.size(); i++) {
                System.out.println((i + 1) + ". " + teachers.get(i).getDisplayName());
            }
            System.out.print("Select head teacher: ");
            String ht = scanner.nextLine().trim();
            try {
                int hChoice = Integer.parseInt(ht);
                if (hChoice >= 1 && hChoice <= teachers.size()) {
                    head = teachers.get(hChoice - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("! Invalid head teacher selection. No head teacher was assigned.");
            }
        } else {
            System.out.println("06 Head Teacher: None (optional, assign later when a Teacher exists)");
        }

        System.out.println("\n+---------------- REVIEW DEPARTMENT ------------------------+");
        System.out.printf("| 01 Department ID      : %-34s |\n", id);
        System.out.printf("| 02 Department name    : %-34s |\n", name);
        System.out.printf("| 03 Department code    : %-34s |\n", code);
        System.out.printf("| 04 Office location    : %-34s |\n", office);
        System.out.printf("| 05 Contact email      : %-34s |\n", email);
        System.out.printf("| 06 Head teacher       : %-34s |\n", (head == null ? "None" : head.getDisplayName()));
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save department   [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            Department dept = new Department(id, name, code, office, email, head);
            try {
                dept.validate();
                departments.add(dept);
                System.out.println("+ Department " + code + " registered successfully.");
                if (nested) return dept;
                System.out.println(" [1] Register another   [2] View department   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return registerDepartmentFlow(false);
                if (post.equals("2")) dept.printDetails();
                return dept;
            } catch (ValidationException e) {
                System.out.println("! Validation failed: " + e.getMessage());
            }
        }
        return null;
    }

    private static Course registerCourseFlow(boolean nested) throws DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| COURSE REGISTRATION                          Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        String code = "";
        String title = "";
        int creditHours = 3;
        Department dept = null;
        Teacher teacher = null;

        while (true) {
            System.out.print("01 Course ID [String | unique | 4-30 chars] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Course ID.");
                continue;
            }
            id = in;
            break;
        }

        while (true) {
            System.out.print("02 Course code [String | unique] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() || !isCourseCodeUnique(in, null)) {
                System.out.println("! Invalid or duplicate Course code.");
                continue;
            }
            code = in;
            break;
        }

        while (true) {
            System.out.print("03 Course title [String | 2-120 chars] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 2 || in.length() > 120) {
                System.out.println("! Must be 2-120 characters.");
                continue;
            }
            title = in;
            break;
        }

        while (true) {
            System.out.print("04 Credit hours [int | 1-6] [3] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                int ch = Integer.parseInt(in);
                if (ch < 1 || ch > 6) {
                    System.out.println("! Credit hours must be between 1 and 6.");
                    continue;
                }
                creditHours = ch;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a number between 1 and 6.");
            }
        }

        dept = selectDepartmentWithAdd();
        if (dept == null) return null;

        teacher = selectTeacherWithAdd(dept);
        if (teacher == null) return null;

        System.out.println("\n+---------------- REVIEW COURSE ----------------------------+");
        System.out.printf("| 01 Course ID          : %-34s |\n", id);
        System.out.printf("| 02 Course code        : %-34s |\n", code);
        System.out.printf("| 03 Course title       : %-34s |\n", title);
        System.out.printf("| 04 Credit hours       : %-34s |\n", creditHours);
        System.out.printf("| 05 Department         : %-34s |\n", dept.getDepartmentName());
        System.out.printf("| 06 Assigned teacher   : %-34s |\n", teacher.getDisplayName());
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save course       [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            Course c = new Course(id, code, title, creditHours, dept, teacher);
            try {
                c.validate();
                courses.add(c);
                System.out.println("+ Course " + code + " registered successfully.");
                if (nested) return c;
                System.out.println(" [1] Register another   [2] View course   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return registerCourseFlow(false);
                if (post.equals("2")) c.printDetails();
                return c;
            } catch (ValidationException e) {
                System.out.println("! Validation failed: " + e.getMessage());
            }
        }
        return null;
    }

    private static Classroom registerClassroomFlow(boolean nested) throws DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| CLASSROOM REGISTRATION                       Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        String bldg = "";
        String room = "";
        int cap = 30;
        int floor = 0;
        boolean avail = true;

        while (true) {
            System.out.print("01 Classroom ID [String | unique | 4-30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Classroom ID.");
                continue;
            }
            id = in;
            break;
        }

        while (true) {
            System.out.print("02 Building name [String | nonblank] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) {
                System.out.println("! Building name is required.");
                continue;
            }
            bldg = in;
            break;
        }

        while (true) {
            System.out.print("03 Room number [String | nonblank] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) {
                System.out.println("! Room number is required.");
                continue;
            }
            room = in;
            break;
        }

        while (true) {
            System.out.print("04 Capacity [int | > 0] [30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                int c = Integer.parseInt(in);
                if (c <= 0) {
                    System.out.println("! Capacity must be greater than zero.");
                    continue;
                }
                cap = c;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a positive whole number.");
            }
        }

        while (true) {
            System.out.print("05 Floor number [int | >= 0] [0] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                int f = Integer.parseInt(in);
                if (f < 0) {
                    System.out.println("! Floor number cannot be negative.");
                    continue;
                }
                floor = f;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a non-negative whole number.");
            }
        }

        while (true) {
            System.out.print("06 Available [1 YES / 2 NO] [YES] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            if (in.equals("1") || in.equalsIgnoreCase("YES")) { avail = true; break; }
            if (in.equals("2") || in.equalsIgnoreCase("NO")) { avail = false; break; }
            System.out.println("! Enter 1 for YES or 2 for NO.");
        }

        System.out.println("\n+---------------- REVIEW CLASSROOM -------------------------+");
        System.out.printf("| 01 Classroom ID       : %-34s |\n", id);
        System.out.printf("| 02 Building name      : %-34s |\n", bldg);
        System.out.printf("| 03 Room number        : %-34s |\n", room);
        System.out.printf("| 04 Capacity           : %-34s |\n", cap);
        System.out.printf("| 05 Floor number       : %-34s |\n", floor);
        System.out.printf("| 06 Available          : %-34s |\n", (avail ? "YES" : "NO"));
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save classroom    [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            Classroom cr = new Classroom(id, bldg, room, cap, floor, avail);
            try {
                cr.validate();
                classrooms.add(cr);
                System.out.println("+ Classroom " + id + " registered successfully.");
                if (nested) return cr;
                System.out.println(" [1] Register another   [2] View classroom   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return registerClassroomFlow(false);
                if (post.equals("2")) cr.printDetails();
                return cr;
            } catch (ValidationException e) {
                System.out.println("! Validation failed: " + e.getMessage());
            }
        }
        return null;
    }

    private static Enrollment enrollStudentFlow(boolean nested) throws DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| ENROLL STUDENT                               Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        Student stu = null;
        Course crs = null;
        LocalDate date = LocalDate.now();
        String semester = "SEMESTER 1";
        String status = "ACTIVE";

        while (true) {
            System.out.print("01 Enrollment ID [String | unique | 4-30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Enrollment ID.");
                continue;
            }
            id = in;
            break;
        }

        stu = selectStudentWithAdd();
        if (stu == null) return null;

        crs = selectCourseWithAdd();
        if (crs == null) return null;

        while (true) {
            System.out.print("04 Enrollment date [LocalDate | YYYY-MM-DD] [" + date + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                LocalDate d = LocalDate.parse(in);
                if (d.isAfter(LocalDate.now())) {
                    System.out.println("! Cannot be in the future.");
                    continue;
                }
                date = d;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date format.");
            }
        }

        while (true) {
            System.out.print("05 Semester [1 SEMESTER 1 / 2 SEMESTER 2 / 3 SUMMER] [SEMESTER 1] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            if (in.equals("1") || in.equalsIgnoreCase("SEMESTER 1")) { semester = "SEMESTER 1"; break; }
            if (in.equals("2") || in.equalsIgnoreCase("SEMESTER 2")) { semester = "SEMESTER 2"; break; }
            if (in.equals("3") || in.equalsIgnoreCase("SUMMER")) { semester = "SUMMER"; break; }
            System.out.println("! Choose 1, 2, or 3.");
        }

        for (Enrollment existing : enrollments) {
            if (existing.getStudent().getStudentNumber().equalsIgnoreCase(stu.getStudentNumber()) &&
                existing.getCourse().getCourseCode().equalsIgnoreCase(crs.getCourseCode()) &&
                existing.getSemester().equalsIgnoreCase(semester) &&
                existing.isActive()) {
                throw new DuplicateRecordException("This student already has an active enrollment in this course and semester.");
            }
        }

        System.out.println("\n+---------------- REVIEW ENROLLMENT ------------------------+");
        System.out.printf("| 01 Enrollment ID      : %-34s |\n", id);
        System.out.printf("| 02 Student            : %-34s |\n", stu.getDisplayName());
        System.out.printf("| 03 Course             : %-34s |\n", crs.getCourseTitle());
        System.out.printf("| 04 Enrollment date    : %-34s |\n", date);
        System.out.printf("| 05 Semester           : %-34s |\n", semester);
        System.out.printf("| 06 Status             : %-34s |\n", status);
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save enrollment   [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            Enrollment e = new Enrollment(id, stu, crs, date, semester, status);
            try {
                e.validate();
                enrollments.add(e);
                System.out.println("+ Enrollment " + id + " saved successfully.");
                if (nested) return e;
                System.out.println(" [1] Register another   [2] View enrollment   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return enrollStudentFlow(false);
                if (post.equals("2")) e.printDetails();
                return e;
            } catch (ValidationException ex) {
                System.out.println("! Validation failed: " + ex.getMessage());
            }
        }
        return null;
    }

    private static Assessment recordAssessmentFlow(boolean nested) throws DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| RECORD ASSESSMENT                            Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        Enrollment enr = null;
        String type = "QUIZ";
        double maxScore = 100.0;
        double obtScore = 0.0;
        LocalDate date = LocalDate.now();

        while (true) {
            System.out.print("01 Assessment ID [String | unique | 4-30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Assessment ID.");
                continue;
            }
            id = in;
            break;
        }

        enr = selectEnrollmentWithAdd();
        if (enr == null) return null;

        while (true) {
            System.out.print("03 Type [1 QUIZ / 2 ASSIGNMENT / 3 MIDTERM / 4 FINAL / 5 PROJECT] [QUIZ] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            if (in.equals("1") || in.equalsIgnoreCase("QUIZ")) { type = "QUIZ"; break; }
            if (in.equals("2") || in.equalsIgnoreCase("ASSIGNMENT")) { type = "ASSIGNMENT"; break; }
            if (in.equals("3") || in.equalsIgnoreCase("MIDTERM")) { type = "MIDTERM"; break; }
            if (in.equals("4") || in.equalsIgnoreCase("FINAL")) { type = "FINAL"; break; }
            if (in.equals("5") || in.equalsIgnoreCase("PROJECT")) { type = "PROJECT"; break; }
            System.out.println("! Choose a valid assessment type.");
        }

        while (true) {
            System.out.print("04 Maximum score [double | > 0] [100.0] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                double ms = Double.parseDouble(in);
                if (ms <= 0) {
                    System.out.println("! Maximum score must be strictly positive.");
                    continue;
                }
                maxScore = ms;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a valid decimal number.");
            }
        }

        while (true) {
            System.out.print("05 Obtained score [double | 0.0 - " + maxScore + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            try {
                double os = Double.parseDouble(in);
                if (os < 0 || os > maxScore) {
                    System.out.println("! Obtained score must be between 0 and " + maxScore + ".");
                    continue;
                }
                obtScore = os;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a valid decimal number.");
            }
        }

        while (true) {
            System.out.print("06 Assessment date [LocalDate | YYYY-MM-DD] [" + date + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                LocalDate d = LocalDate.parse(in);
                if (d.isAfter(LocalDate.now())) {
                    System.out.println("! Cannot be in the future.");
                    continue;
                }
                date = d;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date format.");
            }
        }

        System.out.println("\n+---------------- REVIEW ASSESSMENT ------------------------+");
        System.out.printf("| 01 Assessment ID      : %-34s |\n", id);
        System.out.printf("| 02 Enrollment         : %-34s |\n", enr.getDisplayName());
        System.out.printf("| 03 Assessment type    : %-34s |\n", type);
        System.out.printf("| 04 Maximum score      : %-34s |\n", maxScore);
        System.out.printf("| 05 Obtained score     : %-34s |\n", obtScore);
        System.out.printf("| 06 Assessment date    : %-34s |\n", date);
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save assessment   [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            Assessment a = new Assessment(id, enr, type, maxScore, obtScore, date);
            try {
                a.validate();
                assessments.add(a);
                System.out.println("+ Assessment " + id + " recorded successfully.");
                if (nested) return a;
                System.out.println(" [1] Record another   [2] View assessment   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return recordAssessmentFlow(false);
                if (post.equals("2")) a.printDetails();
                return a;
            } catch (ValidationException ex) {
                System.out.println("! Validation failed: " + ex.getMessage());
            }
        }
        return null;
    }

    private static FeePayment recordFeePaymentFlow(boolean nested) throws DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| RECORD FEE PAYMENT                           Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        Student stu = null;
        BigDecimal amount = BigDecimal.ZERO;
        LocalDate date = LocalDate.now();
        String method = "BANK";
        String receipt = "";

        while (true) {
            System.out.print("01 Payment ID [String | unique | 4-30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Payment ID.");
                continue;
            }
            id = in;
            break;
        }

        stu = selectStudentWithAdd();
        if (stu == null) return null;

        while (true) {
            System.out.print("03 Amount [BigDecimal | > 0] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            try {
                BigDecimal val = new BigDecimal(in);
                if (val.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("! Amount must be strictly positive.");
                    continue;
                }
                amount = val;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a valid monetary amount.");
            }
        }

        while (true) {
            System.out.print("04 Payment date [LocalDate | YYYY-MM-DD] [" + date + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                LocalDate d = LocalDate.parse(in);
                if (d.isAfter(LocalDate.now())) {
                    System.out.println("! Cannot be in the future.");
                    continue;
                }
                date = d;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date format.");
            }
        }

        while (true) {
            System.out.print("05 Method [1 CASH / 2 BANK / 3 MOBILE MONEY / 4 CARD] [BANK] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            if (in.equals("1") || in.equalsIgnoreCase("CASH")) { method = "CASH"; break; }
            if (in.equals("2") || in.equalsIgnoreCase("BANK")) { method = "BANK"; break; }
            if (in.equals("3") || in.equalsIgnoreCase("MOBILE MONEY")) { method = "MOBILE MONEY"; break; }
            if (in.equals("4") || in.equalsIgnoreCase("CARD")) { method = "CARD"; break; }
            System.out.println("! Choose a valid method.");
        }

        while (true) {
            System.out.print("06 Receipt number [String | unique | nonblank] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() || !isReceiptNumberUnique(in, null)) {
                System.out.println("! Receipt number is required and must be unique.");
                continue;
            }
            receipt = in;
            break;
        }

        System.out.println("\n+---------------- REVIEW FEE PAYMENT -----------------------+");
        System.out.printf("| 01 Payment ID         : %-34s |\n", id);
        System.out.printf("| 02 Student            : %-34s |\n", stu.getDisplayName());
        System.out.printf("| 03 Amount             : %-34s |\n", amount);
        System.out.printf("| 04 Payment date       : %-34s |\n", date);
        System.out.printf("| 05 Payment method     : %-34s |\n", method);
        System.out.printf("| 06 Receipt number     : %-34s |\n", receipt);
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save payment      [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            FeePayment fp = new FeePayment(id, stu, amount, date, method, receipt);
            try {
                fp.validate();
                payments.add(fp);
                System.out.println("+ Fee payment " + id + " recorded successfully.");
                if (nested) return fp;
                System.out.println(" [1] Record another   [2] View payment   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return recordFeePaymentFlow(false);
                if (post.equals("2")) fp.printDetails();
                return fp;
            } catch (ValidationException ex) {
                System.out.println("! Validation failed: " + ex.getMessage());
            }
        }
        return null;
    }

    private static LibraryBook registerLibraryBookFlow(boolean nested) throws DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| REGISTER LIBRARY BOOK                        Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        String isbn = "";
        String title = "";
        String author = "";
        int total = 1;
        int avail = 1;

        while (true) {
            System.out.print("01 Book ID [String | unique | 4-30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Book ID.");
                continue;
            }
            id = in;
            break;
        }

        while (true) {
            System.out.print("02 ISBN [String | unique | nonblank] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty() || !isIsbnUnique(in, null)) {
                System.out.println("! ISBN is required and must be unique.");
                continue;
            }
            isbn = in;
            break;
        }

        while (true) {
            System.out.print("03 Title [String | nonblank] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) {
                System.out.println("! Title is required.");
                continue;
            }
            title = in;
            break;
        }

        while (true) {
            System.out.print("04 Author [String | 2-60 chars] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 2 || in.length() > 60) {
                System.out.println("! Author must be 2-60 characters.");
                continue;
            }
            author = in;
            break;
        }

        while (true) {
            System.out.print("05 Total copies [int | >= 0] [1] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                int tc = Integer.parseInt(in);
                if (tc < 0) {
                    System.out.println("! Total copies cannot be negative.");
                    continue;
                }
                total = tc;
                avail = tc;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a non-negative whole number.");
            }
        }

        while (true) {
            System.out.print("06 Available copies [int | 0 - " + total + "] [" + total + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                int ac = Integer.parseInt(in);
                if (ac < 0 || ac > total) {
                    System.out.println("! Available copies must be between 0 and " + total + ".");
                    continue;
                }
                avail = ac;
                break;
            } catch (NumberFormatException e) {
                System.out.println("! Enter a valid whole number.");
            }
        }

        System.out.println("\n+---------------- REVIEW LIBRARY BOOK ----------------------+");
        System.out.printf("| 01 Book ID            : %-34s |\n", id);
        System.out.printf("| 02 ISBN               : %-34s |\n", isbn);
        System.out.printf("| 03 Title              : %-34s |\n", title);
        System.out.printf("| 04 Author             : %-34s |\n", author);
        System.out.printf("| 05 Total copies       : %-34s |\n", total);
        System.out.printf("| 06 Available copies   : %-34s |\n", avail);
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save book         [2] Edit field        [0] Cancel     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("SAVE")) {
            checkUniqueId(id);
            LibraryBook b = new LibraryBook(id, isbn, title, author, total, avail);
            try {
                b.validate();
                books.add(b);
                System.out.println("+ Library book " + id + " registered successfully.");
                if (nested) return b;
                System.out.println(" [1] Register another   [2] View book   [0] Main menu");
                System.out.print("Select: ");
                String post = scanner.nextLine().trim();
                if (post.equals("1")) return registerLibraryBookFlow(false);
                if (post.equals("2")) b.printDetails();
                return b;
            } catch (ValidationException ex) {
                System.out.println("! Validation failed: " + ex.getMessage());
            }
        }
        return null;
    }

    private static BookLoan borrowBookFlow() throws BookUnavailableException, DuplicateRecordException {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID / SCHOOL MANAGEMENT                                |");
        System.out.println("| BORROW LIBRARY BOOK                          Step 1 of 2   |");
        System.out.println("+------------------------------------------------------------+");

        String id = "";
        Student stu = null;
        LibraryBook book = null;
        LocalDate borrowedDate = LocalDate.now();
        LocalDate dueDate = borrowedDate.plusDays(14);

        while (true) {
            System.out.print("01 Loan ID [String | unique | 4-30] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.length() < 4 || in.length() > 30 || !isIdUnique(in, null)) {
                System.out.println("! Invalid or duplicate Loan ID.");
                continue;
            }
            id = in;
            break;
        }

        stu = selectStudentWithAdd();
        if (stu == null) return null;

        book = selectLibraryBookWithAdd(true);
        if (book == null) return null;

        while (true) {
            System.out.print("04 Borrowed date [LocalDate | YYYY-MM-DD] [" + borrowedDate + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                LocalDate d = LocalDate.parse(in);
                if (d.isAfter(LocalDate.now())) {
                    System.out.println("! Borrowed date cannot be in the future.");
                    continue;
                }
                borrowedDate = d;
                dueDate = borrowedDate.plusDays(14);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date format.");
            }
        }

        while (true) {
            System.out.print("05 Due date [LocalDate | after borrowed] [" + dueDate + "] : ");
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL") || in.equalsIgnoreCase("BACK")) return null;
            if (in.isEmpty()) break;
            try {
                LocalDate d = LocalDate.parse(in);
                if (!d.isAfter(borrowedDate)) {
                    System.out.println("! Due date must be after borrowed date.");
                    continue;
                }
                dueDate = d;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date format.");
            }
        }

        System.out.println("\n+---------------- REVIEW BOOK LOAN -------------------------+");
        System.out.printf("| 01 Loan ID            : %-34s |\n", id);
        System.out.printf("| 02 Student            : %-34s |\n", stu.getDisplayName());
        System.out.printf("| 03 Book               : %-34s |\n", book.getTitle());
        System.out.printf("| 04 Borrowed date      : %-34s |\n", borrowedDate);
        System.out.printf("| 05 Due date           : %-34s |\n", dueDate);
        System.out.printf("| 06 Returned date      : %-34s |\n", "Not returned");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Confirm loan      [2] Cancel                                   |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String opt = scanner.nextLine().trim();

        if (opt.equals("1") || opt.equalsIgnoreCase("CONFIRM")) {
            checkUniqueId(id);
            BookLoan loan = new BookLoan(id, stu, book, borrowedDate, dueDate, null);
            try {
                loan.validate();
                book.borrowCopy();
                loans.add(loan);
                System.out.println("+ Book borrowed successfully. Available copies remaining: " + book.getAvailableCopies());
                return loan;
            } catch (ValidationException e) {
                System.out.println("! Validation failed: " + e.getMessage());
            }
        } else {
            System.out.println("Borrowing cancelled. Stock was not changed.");
        }
        return null;
    }

    private static void returnBookFlow() throws ValidationException, RecordNotFoundException {
        System.out.println("\n--- RETURN LIBRARY BOOK ---");
        List<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan l : loans) {
            if (l.isActive()) activeLoans.add(l);
        }

        if (activeLoans.isEmpty()) {
            throw new RecordNotFoundException("There are no books currently on loan.");
        }

        System.out.println("\nACTIVE BOOK LOANS");
        for (int i = 0; i < activeLoans.size(); i++) {
            BookLoan l = activeLoans.get(i);
            System.out.printf("%d. [%s] %s borrowed '%s' (Due: %s)\n", (i + 1), l.getId(), l.getStudent().getDisplayName(), l.getBook().getTitle(), l.getDueDate());
        }

        System.out.print("Select loan to return (0 to cancel): ");
        String in = scanner.nextLine().trim();
        try {
            int sel = Integer.parseInt(in);
            if (sel == 0) return;
            if (sel < 1 || sel > activeLoans.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            BookLoan loan = activeLoans.get(sel - 1);
            LocalDate retDate = LocalDate.now();
            System.out.print("Enter return date [YYYY-MM-DD] [" + retDate + "]: ");
            String rStr = scanner.nextLine().trim();
            if (!rStr.isEmpty()) {
                retDate = LocalDate.parse(rStr);
            }

            if (retDate.isBefore(loan.getBorrowedDate())) {
                throw new ValidationException("Return date cannot be before the borrowed date.");
            }

            if (!confirm("Confirm return of this book?")) {
                System.out.println("Return cancelled. Stock was not changed.");
                return;
            }

            loan.getBook().returnCopy();
            loan.setReturnedDate(retDate);
            System.out.println("+ Book returned successfully. Available copies: " + loan.getBook().getAvailableCopies());

        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid return date format.");
        }
    }

    private static void viewAllRecords() {
        System.out.println("\n============== ALL REGISTERED RECORDS ==============");
        List<Identifiable> all = getAllRecords();
        if (all.isEmpty()) {
            System.out.println("No records have been registered yet.");
            return;
        }

        for (Identifiable item : all) {
            System.out.println("\n----------------------------------------");
            System.out.println("ID: " + item.getId() + " | Active: " + item.isActive());
            if (item instanceof Registrable) {
                ((Registrable) item).printDetails();
            } else {
                System.out.println(item.getDisplayName());
            }
        }
    }

    private static void searchRecord() throws RecordNotFoundException {
        System.out.println("\n--- SEARCH RECORD BY ID ---");
        System.out.print("Enter record ID or Code: ");
        String query = scanner.nextLine().trim();

        for (Department d : departments) {
            if (d.getId().equalsIgnoreCase(query) || d.getDepartmentCode().equalsIgnoreCase(query)) {
                System.out.println("\nDepartment Record Found:");
                d.printDetails();
                return;
            }
        }
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(query) || s.getStudentNumber().equalsIgnoreCase(query)) {
                System.out.println("\nStudent Record Found:");
                s.printDetails();
                return;
            }
        }
        for (Teacher t : teachers) {
            if (t.getId().equalsIgnoreCase(query) || t.getEmployeeNumber().equalsIgnoreCase(query)) {
                System.out.println("\nTeacher Record Found:");
                t.printDetails();
                return;
            }
        }
        for (Administrator a : administrators) {
            if (a.getId().equalsIgnoreCase(query) || a.getStaffNumber().equalsIgnoreCase(query)) {
                System.out.println("\nAdministrator Record Found:");
                a.printDetails();
                return;
            }
        }
        for (Course c : courses) {
            if (c.getId().equalsIgnoreCase(query) || c.getCourseCode().equalsIgnoreCase(query)) {
                System.out.println("\nCourse Record Found:");
                c.printDetails();
                return;
            }
        }
        for (Classroom cr : classrooms) {
            if (cr.getId().equalsIgnoreCase(query)) {
                System.out.println("\nClassroom Record Found:");
                cr.printDetails();
                return;
            }
        }
        for (Enrollment e : enrollments) {
            if (e.getId().equalsIgnoreCase(query)) {
                System.out.println("\nEnrollment Record Found:");
                e.printDetails();
                return;
            }
        }
        for (Assessment as : assessments) {
            if (as.getId().equalsIgnoreCase(query)) {
                System.out.println("\nAssessment Record Found:");
                as.printDetails();
                return;
            }
        }
        for (FeePayment fp : payments) {
            if (fp.getId().equalsIgnoreCase(query) || fp.getReceiptNumber().equalsIgnoreCase(query)) {
                System.out.println("\nFee Payment Record Found:");
                fp.printDetails();
                return;
            }
        }
        for (LibraryBook b : books) {
            if (b.getId().equalsIgnoreCase(query) || b.getIsbn().equalsIgnoreCase(query)) {
                System.out.println("\nLibrary Book Record Found:");
                b.printDetails();
                return;
            }
        }
        for (BookLoan bl : loans) {
            if (bl.getId().equalsIgnoreCase(query)) {
                System.out.println("\nBook Loan Record Found:");
                bl.printDetails();
                return;
            }
        }

        throw new RecordNotFoundException("No record was found matching ID: " + query);
    }

    private static void updateRecord() throws RecordNotFoundException, ValidationException, DuplicateRecordException {
        System.out.println("\n--- UPDATE EXISTING RECORD ---");
        System.out.print("Enter record ID or code to update: ");
        String query = scanner.nextLine().trim();

        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(query) || s.getStudentNumber().equalsIgnoreCase(query)) {
                System.out.println("Selected student: " + s.getDisplayName());
                System.out.println("1. Program name: " + s.getProgramName());
                System.out.println("2. Study year: " + s.getStudyYear());
                System.out.println("3. Guardian phone: " + s.getGuardianPhone());
                System.out.println("4. Scholarship: " + (s.isScholarshipStatus() ? "YES" : "NO"));
                System.out.println("5. Academic status: " + s.getAcademicStatus());
                System.out.println("6. Advisor name: " + s.getAdvisorName());
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New program name: ");
                    String np = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) s.setProgramName(np);
                } else if (opt == 2) {
                    System.out.print("New study year (1-6): ");
                    int y = Integer.parseInt(scanner.nextLine().trim());
                    if (y < 1 || y > 6) throw new ValidationException("Study year must be 1-6.");
                    if (confirm("Confirm update?")) s.setStudyYear(y);
                } else if (opt == 3) {
                    System.out.print("New guardian phone: ");
                    String p = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) s.setGuardianPhone(p);
                } else if (opt == 4) {
                    boolean sc = confirm("Has scholarship?");
                    s.setScholarshipStatus(sc);
                } else if (opt == 5) {
                    System.out.print("New academic status (ACTIVE/INACTIVE): ");
                    String st = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) s.setAcademicStatus(st);
                } else if (opt == 6) {
                    System.out.print("New advisor name: ");
                    String an = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) s.setAdvisorName(an);
                }
                s.validate();
                System.out.println("+ Student record updated.");
                return;
            }
        }

        for (Teacher t : teachers) {
            if (t.getId().equalsIgnoreCase(query) || t.getEmployeeNumber().equalsIgnoreCase(query)) {
                System.out.println("Selected teacher: " + t.getDisplayName());
                System.out.println("1. Specialization: " + t.getSpecialization());
                System.out.println("2. Salary: " + t.getSalary());
                System.out.println("3. Office number: " + t.getOfficeNumber());
                System.out.println("4. Employment type: " + t.getEmploymentType());
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New specialization: ");
                    String sp = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) t.setSpecialization(sp);
                } else if (opt == 2) {
                    System.out.print("New salary: ");
                    BigDecimal sal = new BigDecimal(scanner.nextLine().trim());
                    if (sal.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("Salary must be positive.");
                    if (confirm("Confirm update?")) t.setSalary(sal);
                } else if (opt == 3) {
                    System.out.print("New office number: ");
                    String off = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) t.setOfficeNumber(off);
                } else if (opt == 4) {
                    System.out.print("New employment type: ");
                    String et = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) t.setEmploymentType(et);
                }
                t.validate();
                System.out.println("+ Teacher record updated.");
                return;
            }
        }

        for (Department d : departments) {
            if (d.getId().equalsIgnoreCase(query) || d.getDepartmentCode().equalsIgnoreCase(query)) {
                System.out.println("Selected department: " + d.getDepartmentName());
                System.out.println("1. Department name: " + d.getDepartmentName());
                System.out.println("2. Office location: " + d.getOfficeLocation());
                System.out.println("3. Contact email: " + d.getContactEmail());
                System.out.println("4. Assign Head Teacher");
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New name: ");
                    String nm = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) d.setDepartmentName(nm);
                } else if (opt == 2) {
                    System.out.print("New office: ");
                    String off = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) d.setOfficeLocation(off);
                } else if (opt == 3) {
                    System.out.print("New email: ");
                    String em = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) d.setContactEmail(em);
                } else if (opt == 4) {
                    Teacher head = selectTeacherWithAdd(d);
                    if (confirm("Confirm assigning head teacher?")) d.setHeadTeacher(head);
                }
                d.validate();
                System.out.println("+ Department record updated.");
                return;
            }
        }

        for (Course c : courses) {
            if (c.getId().equalsIgnoreCase(query) || c.getCourseCode().equalsIgnoreCase(query)) {
                System.out.println("Selected course: " + c.getCourseTitle());
                System.out.println("1. Course title: " + c.getCourseTitle());
                System.out.println("2. Credit hours: " + c.getCreditHours());
                System.out.println("3. Reassign Teacher");
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New title: ");
                    String nt = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) c.setCourseTitle(nt);
                } else if (opt == 2) {
                    System.out.print("New credit hours (1-6): ");
                    int ch = Integer.parseInt(scanner.nextLine().trim());
                    if (confirm("Confirm update?")) c.setCreditHours(ch);
                } else if (opt == 3) {
                    Teacher t = selectTeacherWithAdd(c.getDepartment());
                    if (confirm("Confirm reassigning teacher?")) c.setAssignedTeacher(t);
                }
                c.validate();
                System.out.println("+ Course record updated.");
                return;
            }
        }

        for (Classroom cr : classrooms) {
            if (cr.getId().equalsIgnoreCase(query)) {
                System.out.println("Selected classroom: " + cr.getDisplayName());
                System.out.println("1. Capacity: " + cr.getCapacity());
                System.out.println("2. Availability: " + (cr.isAvailable() ? "YES" : "NO"));
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New capacity: ");
                    int cap = Integer.parseInt(scanner.nextLine().trim());
                    if (cap <= 0) throw new ValidationException("Capacity must be positive.");
                    if (confirm("Confirm update?")) cr.setCapacity(cap);
                } else if (opt == 2) {
                    boolean av = confirm("Is classroom available?");
                    cr.setAvailable(av);
                }
                cr.validate();
                System.out.println("+ Classroom record updated.");
                return;
            }
        }

        for (LibraryBook b : books) {
            if (b.getId().equalsIgnoreCase(query) || b.getIsbn().equalsIgnoreCase(query)) {
                System.out.println("Selected book: " + b.getTitle());
                System.out.println("1. Title: " + b.getTitle());
                System.out.println("2. Author: " + b.getAuthor());
                System.out.println("3. Total copies: " + b.getTotalCopies());
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New title: ");
                    String nt = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) b.setTitle(nt);
                } else if (opt == 2) {
                    System.out.print("New author: ");
                    String na = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) b.setAuthor(na);
                } else if (opt == 3) {
                    System.out.print("New total copies: ");
                    int tc = Integer.parseInt(scanner.nextLine().trim());
                    if (tc < b.getTotalCopies() - b.getAvailableCopies()) {
                        throw new ValidationException("Total copies cannot be less than borrowed copies.");
                    }
                    int diff = tc - b.getTotalCopies();
                    b.setTotalCopies(tc);
                    b.setAvailableCopies(b.getAvailableCopies() + diff);
                }
                b.validate();
                System.out.println("+ Library book updated.");
                return;
            }
        }

        for (Administrator a : administrators) {
            if (a.getId().equalsIgnoreCase(query) || a.getStaffNumber().equalsIgnoreCase(query)) {
                System.out.println("Selected administrator: " + a.getDisplayName());
                System.out.println("1. Job title: " + a.getJobTitle());
                System.out.println("2. Assigned office: " + a.getAssignedOffice());
                System.out.println("3. Access level: " + a.getAccessLevel());
                System.out.println("4. Active status: " + (a.isActive() ? "YES" : "NO"));
                System.out.print("Choose field to update: ");
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt == 1) {
                    System.out.print("New job title: ");
                    String jt = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) a.setJobTitle(jt);
                } else if (opt == 2) {
                    System.out.print("New assigned office: ");
                    String ao = scanner.nextLine().trim();
                    if (confirm("Confirm update?")) a.setAssignedOffice(ao);
                } else if (opt == 3) {
                    System.out.print("New access level: ");
                    int al = Integer.parseInt(scanner.nextLine().trim());
                    if (al < 1) throw new ValidationException("Access level must be at least 1.");
                    if (confirm("Confirm update?")) a.setAccessLevel(al);
                } else if (opt == 4) {
                    boolean act = confirm("Is administrator active?");
                    a.setActive(act);
                }
                a.validate();
                System.out.println("+ Administrator updated.");
                return;
            }
        }

        throw new RecordNotFoundException("No record exists matching ID: " + query);
    }

    private static void showStudentAcademicSummary() throws DuplicateRecordException {
        System.out.println("\n--- STUDENT ACADEMIC SUMMARY ---");
        Student stu = selectStudentWithAdd();
        if (stu == null) return;

        System.out.println("\nSTUDENT");
        System.out.println(stu.getRegistrationSummary());

        System.out.println("\nENROLLMENTS & ASSESSMENTS");
        boolean found = false;
        for (Enrollment e : enrollments) {
            if (e.getStudent().getStudentNumber().equalsIgnoreCase(stu.getStudentNumber())) {
                found = true;
                System.out.println("\n" + e.getRegistrationSummary());
                System.out.println("ASSESSMENTS:");
                boolean hasAss = false;
                for (Assessment a : assessments) {
                    if (a.getEnrollment().getId().equalsIgnoreCase(e.getId())) {
                        hasAss = true;
                        System.out.printf(" - [%s] %s | Score: %.1f/%.1f | Date: %s\n", a.getId(), a.getAssessmentType(), a.getObtainedScore(), a.getMaximumScore(), a.getAssessmentDate());
                    }
                }
                if (!hasAss) {
                    System.out.println(" (No assessments recorded for this enrollment yet)");
                }
            }
        }
        if (!found) {
            System.out.println("This student has no enrollments yet.");
        }
    }

    private static void showStudentPaymentHistory() throws DuplicateRecordException {
        System.out.println("\n--- STUDENT PAYMENT HISTORY ---");
        Student stu = selectStudentWithAdd();
        if (stu == null) return;

        BigDecimal total = BigDecimal.ZERO;
        boolean found = false;
        for (FeePayment fp : payments) {
            if (fp.getStudent().getStudentNumber().equalsIgnoreCase(stu.getStudentNumber())) {
                found = true;
                System.out.println("\n" + fp.getRegistrationSummary());
                total = total.add(fp.getAmount());
            }
        }
        if (!found) {
            System.out.println("No payment history exists for this student.");
        }
        System.out.println("\nTOTAL PAYMENTS: " + total);
    }

    private static void viewAvailableBooks() {
        System.out.println("\n--- AVAILABLE LIBRARY BOOKS ---");
        boolean found = false;
        for (LibraryBook b : books) {
            if (b.getAvailableCopies() > 0) {
                found = true;
                System.out.println("\n" + b.getRegistrationSummary());
            }
        }
        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n--- RUNTIME POLYMORPHISM ---");

        System.out.println("\n1. PERSON POLYMORPHISM (List<Person>)");
        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);

        if (people.isEmpty()) {
            System.out.println("Register a Student, Teacher or Administrator first.");
        } else {
            for (Person person : people) {
                System.out.println(person.getRoleDescription() + ": " + person.getDisplayName() + " [Active: " + person.isActive() + "]");
            }
        }

        System.out.println("\n2. IDENTIFIABLE POLYMORPHISM (List<Identifiable>)");
        List<Identifiable> identifiableRecords = getAllRecords();
        if (identifiableRecords.isEmpty()) {
            System.out.println("No records exist yet.");
        } else {
            for (Identifiable item : identifiableRecords) {
                System.out.println("ID: " + item.getId() + " | Name: " + item.getDisplayName() + " | Active: " + item.isActive());
            }
        }
    }
}
