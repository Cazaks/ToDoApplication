
package com.todoApplication.services;

import com.todoApplication.datas.models.Task;
import com.todoApplication.datas.models.TaskStatus;
import com.todoApplication.datas.models.User;
import com.todoApplication.datas.repositories.TaskRepository;
import com.todoApplication.datas.repositories.UserRepository;
import com.todoApplication.dtos.requests.TaskRequestDTO;
import com.todoApplication.dtos.responses.TaskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

//    private TaskRepository taskRepository;

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
//
//    public TaskServiceImplementation(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }

    @Override
    public List<TaskResponseDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponseDTO> responseDTOs = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
            taskResponseDTO.setId(task.getId());
            taskResponseDTO.setTitle(task.getTitle());
            taskResponseDTO.setDescription(task.getDescription());
            taskResponseDTO.setDueDate(task.getDueDate().atStartOfDay());

            responseDTOs.add(taskResponseDTO);

        }
        return responseDTOs;

    }

    @Override
    public TaskResponseDTO addTask(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.getTitle() == null || taskRequestDTO.getTitle() .isBlank()) {
            throw new IllegalArgumentException("Abeg wetin be the task title");
        }

        Optional<User> optionalUser = userRepository.findByEmail(taskRequestDTO.getEmail());
        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = optionalUser.get();

        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());
        task.setUser(user);

        task.setStatus(TaskStatus.PENDING);

        Task savedTask = taskRepository.save(task);

        return mapToResponseDTO(savedTask);
    }

    private TaskResponseDTO mapToResponseDTO(Task savedTask) {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(savedTask.getId());
        taskResponseDTO.setTitle(savedTask.getTitle());
        taskResponseDTO.setDescription(savedTask.getDescription());
        taskResponseDTO.setDueDate(savedTask.getDueDate().atStartOfDay());
        taskResponseDTO.setStatus(savedTask.getStatus());

        return taskResponseDTO;
    }

    @Override
    public List<TaskResponseDTO> searchTask(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.getTitle() == null || taskRequestDTO.getTitle() .isBlank()) {
            throw new IllegalArgumentException("Task tittle is required");
        }

        String regexSearch = ".*" + taskRequestDTO.getTitle() + ".*";

        List<Task> findTasks = taskRepository.findTaskByTitle(regexSearch);
        List<TaskResponseDTO> responseDTOs = new ArrayList<>();

        for (Task savedTask : findTasks) {
            TaskResponseDTO taskResponseDTO = mapToResponseDTO(savedTask);
            responseDTOs.add(taskResponseDTO);
        }

        return responseDTOs;
    }

    @Override
    public TaskResponseDTO updateTask(String taskId, TaskRequestDTO taskRequestDTO) {

        if(taskId == null || taskId.isBlank()){
            throw new IllegalArgumentException("Task id is required");
        }

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        Task taskExists = optionalTask.get();

        Optional<User> optionalUser = userRepository.findByEmail(taskRequestDTO.getEmail());
        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        User user = optionalUser.get();

        if(!taskExists.getUserId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action");
        }

        if (taskRequestDTO.getTitle() != null && !taskRequestDTO.getTitle().isBlank()) {
            taskExists.setTitle(taskRequestDTO.getTitle());
        }

        if(taskRequestDTO.getDescription() != null){
            taskExists.setDescription(taskRequestDTO.getDescription());
        }

        if(taskRequestDTO.getDueDate() != null){
            taskExists.setDueDate(taskRequestDTO.getDueDate());
        }

        if(taskRequestDTO.getStatus() != null){
            taskExists.setStatus(taskRequestDTO.getStatus());
        }

        Task updateTask = taskRepository.save(taskExists);

        return mapToResponseDTO(updateTask);

    }

    @Override
    public TaskResponseDTO deleteTask(String taskId) {

        if(taskId == null || taskId.isBlank()){
            throw new IllegalArgumentException("Task id is required");
        }

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        Task deletedTask = optionalTask.get();

        taskRepository.deleteById(taskId);

        return mapToResponseDTO(deletedTask);
    }

    public TaskResponseDTO markTaskAsComplete(String taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if(optionalTask.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        Task task = optionalTask.get();
        task.setStatus(TaskStatus.COMPLETED);

        Task savedTask = taskRepository.save(task);
        return mapToResponseDTO(savedTask);
    }

}
