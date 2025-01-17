package com.example.TodoList.controller;

import com.example.TodoList.models.Task;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.services.TaskListService;
import com.example.TodoList.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskListService taskListService;

    public TaskController(TaskService taskService, TaskListService taskListService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
    }

    // Отображение задач для списка
    @GetMapping("/{listId}")
    public String getTasksByList(@PathVariable Long listId, Model model) {
        TaskList taskList = taskListService.getTaskListById(listId);
        List<Task> tasks = taskService.getTasksByList(taskList);

        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", tasks);

        return "block/tasks"; // Указываем путь к шаблону tasks.html
    }

    // Создание новой задачи
    @PostMapping
    public String createTask(@RequestParam String title, @RequestParam Long listId, Model model) {
        TaskList taskList = taskListService.getTaskListById(listId);

        // Создаём новую задачу
        taskService.createTask(title, taskList);

        // Получаем обновлённый список задач
        List<Task> tasks = taskService.getTasksByList(taskList);

        // Добавляем данные в модель
        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", tasks);

        // Возвращаем фрагмент tasks.html
        return "block/tasks";
    }


    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        Long listId = task.getTaskList().getId();

        // Удаляем задачу
        taskService.deleteTask(id);

        // Получаем обновлённый список задач
        TaskList taskList = taskService.getTaskListById(listId);
        List<Task> tasks = taskService.getTasksByList(taskList);

        // Добавляем данные в модель
        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", tasks);

        // Возвращаем фрагмент tasks.html
        return "block/tasks";
    }

    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        Long listId = task.getTaskList().getId();

        // Переключаем статус задачи
        taskService.toggleTask(id);

        // Получаем обновлённый список задач
        TaskList taskList = taskService.getTaskListById(listId);
        List<Task> tasks = taskService.getTasksByList(taskList);

        // Добавляем данные в модель
        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", tasks);

        // Возвращаем фрагмент tasks.html
        return "block/tasks";
    }

}
