package com.example.TodoList.controller;

import com.example.TodoList.models.Task;
import com.example.TodoList.models.TaskGroup;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.services.TaskGroupService;
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

    private final TaskGroupService taskGroupService;

    public TaskListController(TaskListService taskListService, TaskService taskService, TaskGroupService taskGroupService) {
        this.taskListService = taskListService;
        this.taskService = taskService;
        this.taskGroupService = taskGroupService;
    }

    // Создание нового списка в группе
    @PostMapping
    public String createTaskList(@RequestParam String name, @RequestParam Long groupId, Model model) {
        TaskGroup taskGroup = taskGroupService.getTaskGroupById(groupId);

        // Создаём новый список
        taskListService.createTaskList(name, taskGroup);

        // Получаем обновлённые списки
        List<TaskList> taskLists = taskListService.getTaskListsByGroup(taskGroup);

        // Добавляем данные в модель
        model.addAttribute("taskLists", taskLists);
        model.addAttribute("groupId", groupId);
        model.addAttribute("group", taskGroup);

        // Возвращаем обновлённый HTML-фрагмент `task_list.html`
        return "block/tasks_list";
    }


    // Удаление списка
    @GetMapping("/{id}/delete")
    public String deleteTaskList(@PathVariable Long id, Model model) {
        TaskList taskList = taskListService.getTaskListById(id);
        Long groupId = taskList.getTaskGroup().getId();

        // Удаляем список
        taskListService.deleteTaskList(id);

        // Получаем обновлённые списки
        TaskGroup taskGroup = taskGroupService.getTaskGroupById(groupId);
        List<TaskList> taskLists = taskListService.getTaskListsByGroup(taskGroup);

        // Добавляем данные в модель
        model.addAttribute("taskLists", taskLists);
        model.addAttribute("groupId", groupId);
        model.addAttribute("group", taskGroup);

        return "block/tasks_list";
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
