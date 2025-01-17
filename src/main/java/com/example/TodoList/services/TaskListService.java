package com.example.TodoList.services;

import com.example.TodoList.models.Task;
import com.example.TodoList.models.TaskGroup;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.repository.TaskListRepository;
import com.example.TodoList.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public TaskListService(TaskListRepository taskListRepository, TaskRepository taskRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskList createTaskList(String name, TaskGroup taskGroup) {
        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setTaskGroup(taskGroup);
        taskGroup.getTaskLists().add(taskList);
        return taskListRepository.save(taskList);
    }

    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    public TaskList getTaskListById(Long id) {
        return taskListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));
    }

    @Transactional
    public void deleteTaskList(Long id) {
        TaskList taskList = taskListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found"));

        // Удаляем связанные задачи напрямую через TaskRepository
        taskRepository.deleteAll(taskList.getTasks());

        // Удаляем сам список задач
        taskListRepository.delete(taskList);
    }

    public List<TaskList> getTaskListsByGroup(TaskGroup group) {
        return taskListRepository.findByTaskGroup(group);
    }
}

