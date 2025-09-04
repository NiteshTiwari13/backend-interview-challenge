package com.example.Task.Api.ServiceSync;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Task.Api.Entity.SyncStatus;
import com.example.Task.Api.Entity.Task;
import com.example.Task.Api.Repository.TaskRepository;
import com.example.Task.Api.ServiceCrud.TaskService;
import com.example.Task.Api.dto.SyncRequestItem;
import com.example.Task.Api.dto.SyncResponseItem;
import com.example.Task.Api.dto.TaskDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class SyncService {

    private final TaskService taskService;
    private final TaskRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${sync.batch-size:50}")
    private int batchSize;

    public SyncService(TaskService taskService, TaskRepository repository) {
        this.taskService = taskService;
        this.repository = repository;
    }

    @Transactional
    public List<SyncResponseItem> processSync(List<SyncRequestItem> queue) {
        List<SyncResponseItem> results = new ArrayList<>();
        if (queue == null || queue.isEmpty()) return results;

        for (int i = 0; i < queue.size(); i += batchSize) {
            int end = Math.min(i + batchSize, queue.size());
            List<SyncRequestItem> batch = queue.subList(i, end);

            for (SyncRequestItem item : batch) {
                try {
                    switch (item.getOp()) {
                        case CREATE -> results.add(handleCreate(item.getTask()));
                        case UPDATE -> results.add(handleUpdate(item.getTask()));
                        case DELETE -> results.add(handleDelete(item.getTask()));
                        default -> results.add(SyncResponseItem.builder()
                                .id(item.getTask() != null ? item.getTask().getId() : null)
                                .success(false)
                                .message("Unknown op")
                                .build());
                    }
                } catch (Exception e) {
                    results.add(SyncResponseItem.builder()
                            .id(item.getTask() != null ? item.getTask().getId() : null)
                            .success(false)
                            .message("Server error: " + e.getMessage())
                            .build());
                }
            }
        }
        return results;
    }

   
    private Task mapToEntity(TaskDto dto) {
        Task t = new Task();
        t.setId(dto.getId());
        t.setTitle(dto.getTitle());
        t.setNotes(dto.getNotes());
        t.setCompleted(dto.isCompleted());
        t.setClientid(dto.getClientid());
        t.setCreatedAt(dto.getCreatedAt());
        t.setUpdatedAt(dto.getUpdatedAt());
        t.setVersion(dto.getVersion());
        if (dto.getIsDeleted() != null) t.setIsDeleted(dto.getIsDeleted());
        if (dto.getSyncStatus() != null) t.setSyncStatus(dto.getSyncStatus());
        return t;
    }

    private String asJson(Task t) {
        try { return mapper.writeValueAsString(t); }
        catch (Exception e) { return null; }
    }

   
    private SyncResponseItem handleCreate(TaskDto dto) {
        if (dto == null) return SyncResponseItem.builder().success(false).message("Missing task").build();

        Task incoming = mapToEntity(dto);

        if (incoming.getId() == null) {
           
            Task saved = taskService.createOrUpdate(incoming);
            return SyncResponseItem.builder()
                    .id(saved.getId()).success(true).message("Task created")
                    .serverTask(asJson(saved)).build();
        }

       
        var existing = repository.findById(incoming.getId());
        if (existing.isPresent()) {
           
            return handleUpdate(dto);
        }

        if (incoming.getCreatedAt() == null) incoming.setCreatedAt(Instant.now());
        if (incoming.getUpdatedAt() == null) incoming.setUpdatedAt(incoming.getCreatedAt());
        incoming.setIsDeleted(false);
        incoming.setSyncStatus(SyncStatus.SYNCED);

        Task saved = repository.save(incoming);
        return SyncResponseItem.builder()
                .id(saved.getId()).success(true).message("Task created")
                .serverTask(asJson(saved)).build();
    }

    private SyncResponseItem handleUpdate(TaskDto dto) {
        if (dto == null || dto.getId() == null)
            return SyncResponseItem.builder().success(false).message("Missing id for update").build();

        var existingOpt = repository.findById(dto.getId());
        if (existingOpt.isEmpty())
            return SyncResponseItem.builder().id(dto.getId()).success(false).message("Task not found for update").build();

        Task existing = existingOpt.get();
        Instant clientTs = Objects.requireNonNullElse(dto.getUpdatedAt(), Instant.now());

       
        if (existing.getUpdatedAt() != null && clientTs.isBefore(existing.getUpdatedAt())) {
            return SyncResponseItem.builder()
                    .id(existing.getId()).success(false)
                    .message("Skipped: server has newer update (" + existing.getUpdatedAt() + ")")
                    .serverTask(asJson(existing)).build();
        }

        existing.setTitle(dto.getTitle());
        existing.setNotes(dto.getNotes());
        existing.setCompleted(dto.isCompleted());
        existing.setClientid(dto.getClientid());
        if (dto.getIsDeleted() != null) existing.setIsDeleted(dto.getIsDeleted());
        if (dto.getSyncStatus() != null) existing.setSyncStatus(dto.getSyncStatus());
        existing.setUpdatedAt(clientTs);

        Task saved = repository.save(existing);
        return SyncResponseItem.builder()
                .id(saved.getId()).success(true).message("Task updated")
                .serverTask(asJson(saved)).build();
    }

    private SyncResponseItem handleDelete(TaskDto dto) {
        if (dto == null || dto.getId() == null)
            return SyncResponseItem.builder().success(false).message("Missing id for delete").build();

        var existingOpt = repository.findById(dto.getId());
        if (existingOpt.isEmpty())
            return SyncResponseItem.builder().id(dto.getId()).success(false).message("Task not found for delete").build();

        Task existing = existingOpt.get();
        Instant clientTs = Objects.requireNonNullElse(dto.getUpdatedAt(), Instant.now());

        if (existing.getUpdatedAt() != null && clientTs.isBefore(existing.getUpdatedAt())) {
            return SyncResponseItem.builder()
                    .id(existing.getId()).success(false)
                    .message("Skipped delete: server has newer update (" + existing.getUpdatedAt() + ")")
                    .serverTask(asJson(existing)).build();
        }

        existing.setIsDeleted(true);
        existing.setSyncStatus(SyncStatus.SYNCED);
        existing.setUpdatedAt(clientTs);

        repository.save(existing);

        return SyncResponseItem.builder()
                .id(existing.getId()).success(true).message("Task deleted (soft)")
                .serverTask(asJson(existing)).build();
    }

    // Status snapshot (for UI)
    public SyncStatusSummary status() {
        long total = repository.count();
        long active = repository.countByIsDeletedFalse();
        long deleted = repository.countByIsDeletedTrue();
        return new SyncStatusSummary(total, active, deleted);
    }

    public record SyncStatusSummary(long total, long active, long deleted) {}
}
