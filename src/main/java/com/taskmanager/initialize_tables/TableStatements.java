package com.taskmanager.initialize_tables;

public interface TableStatements {
    String TASK = "CREATE TABLE IF EXIST task (id SERIAL PRIMARY KEY, title character varying, description character varying, due_date date, priority_level character varying, status character varying);";
}
