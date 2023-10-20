package com.mjc.school;

import com.mjc.school.controller.commands.Command;
import com.mjc.school.controller.commands.author.*;
import com.mjc.school.controller.commands.news.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        Scanner scanner = new Scanner(System.in);
        MenuPrinter menuPrinter = new MenuPrinter();
        while (true) {
            menuPrinter.print();
            Command command = null;
            String clause = scanner.nextLine();
            switch (clause) {
                case "1" -> command = applicationContext.getBean(GetAllNewsCommand.class);
                case "2" -> command = applicationContext.getBean(GetNewsByIdCommand.class);
                case "3" -> command = applicationContext.getBean(CreateNewsCommand.class);
                case "4" -> command = applicationContext.getBean(UpdateNewsCommand.class);
                case "5" -> command = applicationContext.getBean(DeleteNewsCommand.class);
                case "6" -> command = applicationContext.getBean(GetAllAuthorsCommand.class);
                case "7" -> command = applicationContext.getBean(GetAuthorByIdCommand.class);
                case "8" -> command = applicationContext.getBean(CreateAuthorCommand.class);
                case "9" -> command = applicationContext.getBean(UpdateAuthorCommand.class);
                case "10" -> command = applicationContext.getBean(DeleteAuthorCommand.class);
                case "0" -> System.exit(0);
                default -> System.out.println("command not found");
            }

            if (command != null) {
                command.execute();
            }

        }
    }
}
