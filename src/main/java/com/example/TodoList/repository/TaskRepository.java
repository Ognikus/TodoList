package com.example.TodoList.repository;

import com.example.TodoList.models.Task;
import com.example.TodoList.models.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.taskList = :taskList")
    List<Task> findByTaskList(@Param("taskList") TaskList taskList);
}
