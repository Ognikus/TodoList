package com.example.TodoList.repository;

import com.example.TodoList.models.TaskGroup;
import com.example.TodoList.models.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByTaskGroup(TaskGroup group);
}
