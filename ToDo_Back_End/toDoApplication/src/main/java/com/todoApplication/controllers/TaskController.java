package com.todoApplication.controllers;

import com.todoApplication.datas.models.Task;
import com.todoApplication.dtos.requests.TaskRequestDTO;
import com.todoApplication.dtos.responses.TaskResponseDTO;
import com.todoApplication.services.TaskService;
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

    @GetMapping("/getAllTask")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @PostMapping("/addTask")
    public ResponseEntity<TaskResponseDTO> addTask(@RequestBody Task task) {

        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tittle is required");
        }
        TaskResponseDTO createdTask = taskService.addTask(new TaskRequestDTO());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

}
