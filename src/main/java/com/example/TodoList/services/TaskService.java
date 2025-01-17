package com.example.TodoList.services;

import com.example.TodoList.models.Task;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListService taskListService;

    public TaskService(TaskRepository taskRepository, TaskListService taskListService) {
        this.taskRepository = taskRepository;
        this.taskListService = taskListService;
    }

    @Transactional
    public Task createTask(String title, TaskList taskList) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setTaskList(taskList);
        taskList.getTasks().add(task);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksByList(TaskList taskList) {
        return taskRepository.findByTaskList(taskList);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        taskRepository.delete(task);
    }

    @Transactional
    public void toggleTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public List<Task> getTasksByList(TaskList taskList) {
        return taskRepository.findByTaskList(taskList);
    }

    public TaskList getTaskListById(Long listId) {
        return taskListService.getTaskListById(listId);
    }
}
