package com.tareas.brayan.Controller;



import com.tareas.brayan.entidades.Task;
import com.tareas.brayan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public String listUserTasks(@RequestParam("userId") String userId, Model model) {
        List<Task> userTasks = taskService.findByUserId(userId);
        model.addAttribute("tasks", userTasks);
        model.addAttribute("userId", userId); // Asegurémonos de pasar userId a la vista
        return "tasks"; 
    }

    @GetMapping("/tasks/add")
    public String addTaskForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("userId", userId);
        return "add-task";
    }

    @PostMapping("/tasks/add")
    public String addTask(Task task, @RequestParam("userId") String userId) {
        task.setUserId(userId);
        taskService.saveTask(task);
        return "redirect:/tasks?userId=" + userId;
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTaskForm(@PathVariable("id") String id, @RequestParam("userId") String userId, Model model) {
        Task task = taskService.findById(id).orElse(null);
        if (task != null && task.getUserId().equals(userId)) { // Verifiquemos que la tarea pertenezca al usuario actual
            model.addAttribute("task", task);
            model.addAttribute("userId", userId);
            return "edit-task";
        } else {
            return "redirect:/tasks?userId=" + userId; // Si la tarea no pertenece al usuario, redireccionemos de vuelta a la página de tareas
        }
    }

    @PostMapping("/tasks/edit")
    public String editTask(Task task, @RequestParam("userId") String userId) {
        task.setUserId(userId);
        taskService.saveTask(task);
        return "redirect:/tasks?userId=" + userId;
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") String id, @RequestParam("userId") String userId) {
        taskService.deleteTask(id);
        return "redirect:/tasks?userId=" + userId;
    }
}
