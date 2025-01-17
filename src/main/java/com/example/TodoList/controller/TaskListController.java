package com.example.TodoList.controller;

import com.example.TodoList.models.Task;
import com.example.TodoList.models.TaskGroup;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.services.TaskListService;
import com.example.TodoList.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskService taskService;

    public TaskListController(TaskListService taskListService, TaskService taskService) {
        this.taskListService = taskListService;
        this.taskService = taskService;
    }

//    // Получение задач списка и отображение блока tasks.html
//    @GetMapping("/{id}/tasks")
//    public String getTaskList(@PathVariable Long id, Model model) {
//        TaskList taskList = taskListService.getTaskListById(id);
//        List<Task> tasks = taskService.getTasksByList(taskList);
//
//        model.addAttribute("taskList", taskList);
//        model.addAttribute("tasks", tasks);
//
//        return "block/tasks"; // Возвращаем блок tasks.html
//    }

    // Создание нового списка в группе
    @PostMapping
    public String createTaskList(@RequestParam String name, @RequestParam Long groupId) {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setId(groupId);
        taskListService.createTaskList(name, taskGroup);
        return "redirect:/groups/" + groupId + "/lists"; // Перенаправление на группу
    }

    // Удаление списка
    @GetMapping("/{id}/delete")
    public String deleteTaskList(@PathVariable Long id) {
        taskListService.deleteTaskList(id);
        return "redirect:/groups"; // Возвращаемся на страницу группы
    }

    @GetMapping("/{listId}/tasks")
    public String getTasksByList(@PathVariable Long listId, Model model) {
        TaskList taskList = taskListService.getTaskListById(listId); // Получаем список по ID
        List<Task> tasks = taskService.getTasksByList(taskList);     // Получаем задачи списка

        model.addAttribute("taskList", taskList);
        model.addAttribute("tasks", tasks);

        return "block/tasks"; // Возвращаем шаблон tasks.html
    }

}
