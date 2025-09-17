package com.todoApplication.servises;

import com.todoApplication.datas.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
    Task addTask(Task task);
}
