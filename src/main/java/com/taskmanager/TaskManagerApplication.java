package com.taskmanager;

import com.taskmanager.dao.TaskDAO;
import com.taskmanager.dao.TasksDaoJdbc;
import com.taskmanager.database.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskManagerApplication {
    public static void main(String[] args) {

        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public TaskDAO tasksDAO() {
        TaskDAO taskDaoJdbc = new TasksDaoJdbc(createDataBase());
        taskDaoJdbc.initializeTables();
        return taskDaoJdbc;
    }

    public Database createDataBase() {
        return new Database(
                "jdbc:postgresql://localhost:5432/task-manager",
                "postgres",
                "postgres");
    }
}