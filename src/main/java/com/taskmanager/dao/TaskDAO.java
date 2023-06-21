package com.taskmanager.dao;

import com.taskmanager.dto.TaskDTO;

import java.util.List;

public interface TaskDAO {
    void initializeTables();

    void sayHi();
    List<TaskDTO> getAllTasks();
    TaskDTO getTaskById(int id);
    int addNewTask(TaskDTO task);
    Boolean deleteTask(int id);

    void editTask(int taskId, TaskDTO task);
}
