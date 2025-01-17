package com.example.TodoList.services;


import com.example.TodoList.models.TaskGroup;
import com.example.TodoList.models.TaskList;
import com.example.TodoList.repository.TaskGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskListService taskListService;

    public TaskGroupService(TaskGroupRepository taskGroupRepository, TaskListService taskListService) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskListService = taskListService;
    }

    @Transactional
    public TaskGroup createTaskGroup(String name) {
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setName(name);
        return taskGroupRepository.save(taskGroup);
    }

    public List<TaskGroup> getAllTaskGroups() {
        return taskGroupRepository.findAll();
    }

    public TaskGroup getTaskGroupById(Long id) {
        return taskGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task group not found"));
    }

    @Transactional
    public void deleteTaskGroup(Long id) {
        TaskGroup taskGroup = taskGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task group not found"));
        for (TaskList taskList : taskGroup.getTaskLists()) {
            taskListService.deleteTaskList(taskList.getId());
        }
        taskGroupRepository.delete(taskGroup);
    }
}

