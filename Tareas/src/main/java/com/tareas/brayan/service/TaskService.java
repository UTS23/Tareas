package com.tareas.brayan.service;

import com.tareas.brayan.entidades.Task;
import com.tareas.brayan.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findByUserId(String userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
    
}
