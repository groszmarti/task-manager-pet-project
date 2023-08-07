package com.taskmanager.dao;

import com.taskmanager.database.Database;
import com.taskmanager.dto.TaskDTO;
import com.taskmanager.initialize_tables.TableInitializer;
import com.taskmanager.initialize_tables.TableStatements;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Status;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TasksDaoJdbc implements TaskDAO{
    private Database database;
    private Map<String, String> tables;

    public TasksDaoJdbc(Database database) {
        this.database = database;
        this.tables = Map.of(
                "task", TableStatements.TASK
        );
    }

    @Override
    public void initializeTables() {
        TableInitializer tableInitializer = new TableInitializer(database, tables);
        tableInitializer.initialize();
    }


    @Override
    public void sayHi() {
        System.out.println("Hi DAO!");
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        String query = "SELECT task.id, task.title, task.description, task.due_date, task.priority_level, task.status FROM task\n" +
                "ORDER BY task.due_date DESC";
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<TaskDTO> tasks = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate taskDueDate = resultSet.getTimestamp("due_date").toLocalDateTime().toLocalDate();
                String enumValueStringPriorityLevel = resultSet.getString("priority_level");
                PriorityLevel priorityLevel = PriorityLevel.valueOf(enumValueStringPriorityLevel);
                String enumValueStringStatus = resultSet.getString("status");
                Status status = Status.valueOf(enumValueStringStatus);
                TaskDTO task = new TaskDTO(id, title, description, taskDueDate, priorityLevel, status);
                tasks.add(task);
            }
            for (TaskDTO task : tasks) {
                System.out.println(task);
            }
            System.out.println(tasks);
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TaskDTO getTaskById(int taskId) {
        String query = "SELECT COUNT(task.id) as task_count FROM task;";
        try (Connection connection = database.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(query);
            //statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate taskDueDate = resultSet.getTimestamp("due_date").toLocalDateTime().toLocalDate();
                String enumValueStringPriorityLevel = resultSet.getString("priority_level");
                PriorityLevel priorityLevel = PriorityLevel.valueOf(enumValueStringPriorityLevel);
                String enumValueStringStatus = resultSet.getString("status");
                Status status = Status.valueOf(enumValueStringStatus);
                TaskDTO task = new TaskDTO(id, title, description, taskDueDate, priorityLevel, status);
                return task;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int addNewTask(TaskDTO task) {
        String query = "INSERT INTO task (title, description, due_date, priority_level, status)" +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            prepareTask(task.title(), task.description(), task.priorityLevel(), task.status(), statement);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    private void prepareTask(String title, String description, PriorityLevel priorityLevel, Status status, PreparedStatement statement) throws SQLException {
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setDate(3, Date.valueOf(LocalDateTime.now().toLocalDate()));
        statement.setString(4,priorityLevel.toString());
        statement.setString(5,status.toString());
    }

    @Override
    public Boolean deleteTask(int taskId) {
        String query = "DELETE FROM task WHERE id = ?";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void editTask(int taskId, TaskDTO task) {
        String query = "UPDATE task\n" +
                "SET title = ?, description = ?, due_date = ?, priority_level = ?, status = ?\n" +
                "WHERE task.id = ?";
        try (Connection connection = database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.title());
            statement.setString(2, task.description());
            statement.setDate(3, Date.valueOf(task.dueDate()));
            statement.setString(4, task.priorityLevel().toString());
            statement.setString(5, task.status().toString());
            statement.setInt(6, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
