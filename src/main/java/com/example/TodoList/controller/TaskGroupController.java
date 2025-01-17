package com.example.TodoList.controller;

import com.example.TodoList.models.TaskGroup;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.services.TaskGroupService;
import com.example.TodoList.services.TaskListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class TaskGroupController {

    private final TaskGroupService taskGroupService;
    private final TaskListService taskListService;

    public TaskGroupController(TaskGroupService taskGroupService, TaskListService taskListService) {
        this.taskGroupService = taskGroupService;
        this.taskListService = taskListService;
    }

    @GetMapping
    public String getTaskGroups(Model model) {
        List<TaskGroup> taskGroups = taskGroupService.getAllTaskGroups();
        model.addAttribute("taskGroups", taskGroups);
        return "task_groups";
    }

    @PostMapping
    public String createTaskGroup(@RequestParam String name) {
        taskGroupService.createTaskGroup(name);
        return "redirect:/groups";
    }

    @PostMapping("/{groupId}/create-list")
    public String createTaskList(@PathVariable Long groupId, @RequestParam String name) {
        TaskGroup taskGroup = taskGroupService.getTaskGroupById(groupId);
        taskListService.createTaskList(name, taskGroup);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/delete")
    public String deleteTaskGroup(@PathVariable Long id) {
        taskGroupService.deleteTaskGroup(id);
        return "redirect:/groups";
    }

    @GetMapping("/{groupId}/lists")
    public String getListsByGroup(@PathVariable("groupId") Long groupId, Model model) {
        TaskGroup group = taskGroupService.getTaskGroupById(groupId);
        List<TaskList> taskLists = taskListService.getTaskListsByGroup(group);
        model.addAttribute("group", group);
        model.addAttribute("taskLists", taskLists);
        return "block/tasks_list"; // Возвращаем только HTML содержимое
    }

}
