package com.example.Task.Api.Entity;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name="task")
public class Task {

    @Id
    private String id;

    private String title;
    private String notes;
    private boolean completed;
    private String clientid;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    private SyncStatus syncStatus = SyncStatus.SYNCED;

    @Version
    private Long version;

    public Task() {}

    public Task(String id, String title, String notes, boolean completed,
                String clientid, Instant updatedAt, Long version) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.completed = completed;
        this.clientid = clientid;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        if (updatedAt == null) updatedAt = Instant.now();
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getClientid() { return clientid; }
    public void setClientid(String clientid) { this.clientid = clientid; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public SyncStatus getSyncStatus() { return syncStatus; }
    public void setSyncStatus(SyncStatus syncStatus) { this.syncStatus = syncStatus; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
