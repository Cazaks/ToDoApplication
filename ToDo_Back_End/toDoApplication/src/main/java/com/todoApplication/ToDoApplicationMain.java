package com.todoApplication;

import com.todoApplication.servises.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ToDoApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ToDoApplicationMain.class, args);
    }
}
