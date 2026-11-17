
package com.auca.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayMenu() {
        System.out.println("\n==========================================");
        System.out.println("          AUCA ID MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1.  Register Department");
        System.out.println("2.  Register Student");
        System.out.println("3.  Register Teacher");
        System.out.println("4.  Register Administrator");
        System.out.println("5.  Register Course");
        System.out.println("6.  Register Classroom");
        System.out.println("7.  Enroll Student");
        System.out.println("8.  Record Assessment");
        System.out.println("9.  Record Fee Payment");
        System.out.println("10. Register Library Book");
        System.out.println("11. Borrow Library Book");
        System.out.println("12. Return Library Book");
        System.out.println("13. View All Records");
        System.out.println("14. Search Record by ID");
        System.out.println("15. Update Existing Record");
        System.out.println("16. Student Academic Summary");
        System.out.println("17. Student Payment History");
        System.out.println("18. View Available Books");
        System.out.println("19. Demonstrate Polymorphism");
        System.out.println("0.  Exit");
        System.out.println("==========================================");
        System.out.print("Enter your choice: ");
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String readRequiredText(String prompt) {
        while (true) {
            String value = readLine(prompt);

            if (!value.isBlank()) {
                return value;
            }

            displayError("This field is required. Please try again.");
        }
    }

    public int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                displayError("Please enter a valid whole number.");
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);

            if (value >= min && value <= max) {
                return value;
            }

            displayError("Enter a number between " + min + " and " + max + ".");
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            String value = readLine(prompt);

            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                displayError("Please enter a valid number.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String value = readLine(prompt);

            try {
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                displayError("Please enter a valid monetary amount.");
            }
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            String value = readLine(prompt);

            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                displayError("Invalid date. Use the format yyyy-MM-dd.");
            }
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            String value = readLine(prompt);

            if (value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("Y")) {
                return true;
            }

            if (value.equalsIgnoreCase("NO") || value.equalsIgnoreCase("N")) {
                return false;
            }

            displayError("Please enter YES or NO.");
        }
    }

    public String readChoice(String prompt, String... options) {
        while (true) {
            String value = readRequiredText(prompt);

            for (String option : options) {
                if (option.equalsIgnoreCase(value)) {
                    return option;
                }
            }

            displayError("Invalid choice. Available options: "
                    + String.join(", ", options));
        }
    }

    public int readMenuChoice() {
        return readInt("Enter your choice: ", 0, 19);
    }

    public void displayMessage(String message) {
        System.out.println("\n" + message);
    }

    public void displaySuccess(String message) {
        System.out.println("\n[SUCCESS] " + message);
    }

    public void displayError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void displayWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public void displayRecord(Object record) {
        System.out.println("\n------------------------------------------");
        System.out.println(record);
        System.out.println("------------------------------------------");
    }

    public void displayRecords(List<?> records) {
        if (records == null || records.isEmpty()) {
            displayWarning("No records found.");
            return;
        }

        System.out.println("\n========== RECORDS ==========");

        for (Object record : records) {
            displayRecord(record);
        }

        System.out.println("Total records: " + records.size());
    }

    public void displayOptions(List<?> records, String title) {
        System.out.println("\n========== " + title + " ==========");

        if (records == null || records.isEmpty()) {
            displayWarning("No records available.");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            System.out.println((i + 1) + ". " + records.get(i));
        }
    }

    public void pause() {
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }

    public void displayWelcome() {
        System.out.println("\nWelcome to the AUCA ID Management System.");
    }

    public void displayGoodbye() {
        System.out.println("\nThank you for using AUCA ID Management System.");
        System.out.println("Goodbye!");
    }
}
