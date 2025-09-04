package com.example.Task.Api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Task.Api.Entity.Task;
import com.example.Task.Api.ServiceCrud.TaskService;

@RestController
@RequestMapping({"/api/tasks", "/api/task"})
public class TaskController {

    private final TaskService svc;

    public TaskController(TaskService svc) {
        this.svc = svc;
    }

   
    @GetMapping
    public List<Task> list() {
        return svc.listAll();
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable String id) {
        Task task = svc.getById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task t) {
        Task created = svc.createOrUpdate(t);
        return ResponseEntity.ok(created);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable String id, @RequestBody Task t) {
        t.setId(id);
        Task updated = svc.createOrUpdate(t);
        return ResponseEntity.ok(updated);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        boolean deleted = svc.delete(id);
        return deleted ? ResponseEntity.ok("Task deleted successfully with id: " + id)
                       : ResponseEntity.notFound().build();
    }
}
