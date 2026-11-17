
package com.auca.main;

import com.auca.controller.SchoolController;
import com.auca.repository.SchoolRepository;
import com.auca.service.SchoolService;
import com.auca.view.ConsoleView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SchoolRepository repository = new SchoolRepository();
        SchoolService service = new SchoolService(repository);
        ConsoleView view = new ConsoleView(scanner);
        SchoolController controller = new SchoolController(service, view);

        controller.start();

        scanner.close();
    }
}