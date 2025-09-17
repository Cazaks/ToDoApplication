package com.todoApplication.servises;

import com.todoApplication.datas.models.Task;
import com.todoApplication.datas.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();

    }

    public Task addTask(Task task) {
        if (task.getTittle() == null || task.getTittle() .isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        return taskRepository.save(task);
    }

}
