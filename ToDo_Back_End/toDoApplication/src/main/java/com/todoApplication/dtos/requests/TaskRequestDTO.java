package com.todoApplication.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
}
