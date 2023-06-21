package com.taskmanager.dto;

import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record TaskDTO(int id, String title, String description, LocalDate dueDate, PriorityLevel priorityLevel, Status status) {
}
