package com.todoApplication.controllers;

import com.todoApplication.datas.models.Task;
import com.todoApplication.servises.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        if(task.getTittle() == null || task.getTittle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tittle is required");
        }
        Task createdTask = taskService.addTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

}
