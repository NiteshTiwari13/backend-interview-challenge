package com.example.Task.Api.ServiceCrud;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Task.Api.Entity.SyncStatus;
import com.example.Task.Api.Entity.Task;
import com.example.Task.Api.Repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    
    public List<Task> listAll() {
        return repository.findByIsDeletedFalseOrderByUpdatedAtDesc();
    }

    
    public Task getById(String id) {
        Optional<Task> t = repository.findById(id);
        return t.filter(task -> !task.getIsDeleted()).orElse(null);
    }

   
    @Transactional
    public Task createOrUpdate(Task t) {
        Instant now = Instant.now();

        if (t.getId() == null) {
            t.setId(UUID.randomUUID().toString());
            t.setCreatedAt(now);
        } else {
            repository.findById(t.getId()).ifPresent(db -> t.setCreatedAt(db.getCreatedAt()));
        }

        t.setUpdatedAt(now);
        t.setSyncStatus(SyncStatus.SYNCED);
        if (t.getIsDeleted()) t.setIsDeleted(false); // creating/updating => not deleted

        return repository.save(t);
    }

    
    @Transactional
    public boolean delete(String id) {
        return repository.findById(id).map(db -> {
            db.setIsDeleted(true);
            db.setUpdatedAt(Instant.now());
            db.setSyncStatus(SyncStatus.SYNCED);
            repository.save(db);
            return true;
        }).orElse(false);
    }
}
