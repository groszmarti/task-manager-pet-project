package com.taskmanager.controller;


import com.taskmanager.dto.TaskDTO;
import com.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public TaskDTO getTaskById(@PathVariable int taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/")
    public int addNewTask(@RequestBody TaskDTO task) {
        taskService.addNewTask(task);
        return 0;
    }

    @PatchMapping("/{taskId}")
    public int editTask(@PathVariable int taskId, @RequestBody TaskDTO task) {
        taskService.editTask(taskId, task);
        return 1;
    }


    @DeleteMapping("/{taskId}")
    public boolean deleteTaskById(@PathVariable int taskId) {
        taskService.deleteTaskById(taskId);
        return true;
    }
}

