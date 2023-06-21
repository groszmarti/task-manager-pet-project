package com.taskmanager.service;

import com.taskmanager.dao.TaskDAO;
import com.taskmanager.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskDAO tasksDAO;

    @Autowired
    public TaskService(TaskDAO tasksDAO) {
        this.tasksDAO = tasksDAO;
    }

    public List<TaskDTO> getAllTasks() {
        return  tasksDAO.getAllTasks();
    }

    public TaskDTO getTaskById(int id) {
        // TODO
        return tasksDAO.getTaskById(id);
    }

    public boolean deleteTaskById(int id) {
        return tasksDAO.deleteTask(id);
    }

    public int addNewTask(TaskDTO task) {
        // TODO
        int createdId = 0;
        tasksDAO.addNewTask(task);
        return createdId;
    }


    public int editTask(int taskId, TaskDTO task) {
        tasksDAO.editTask(taskId, task);
        return 1;
    }
}

