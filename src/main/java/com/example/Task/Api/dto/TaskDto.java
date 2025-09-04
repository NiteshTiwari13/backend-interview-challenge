package com.example.Task.Api.dto;

import java.time.Instant;
import com.example.Task.Api.Entity.SyncStatus;

public class TaskDto {

    private String id;
    private String title;
    private String notes;
    private boolean completed;
    private String clientid;
    private Instant createdAt;
    private Instant updatedAt;
    private Long version;
    private Boolean isDeleted;       
    private SyncStatus syncStatus;  

    public TaskDto() {}

    public TaskDto(String id, String title, String notes, boolean completed,
                   String clientid, Instant createdAt, Instant updatedAt,
                   Long version, Boolean isDeleted, SyncStatus syncStatus) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.completed = completed;
        this.clientid = clientid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.isDeleted = isDeleted;
        this.syncStatus = syncStatus;
    }

    // getters/setters
    public String getId() { 
    	return id;
    	}
    public void setId(String id) { 
    	this.id = id; 
    	}
    public String getTitle() {
    	return title;
    	}
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
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
    public SyncStatus getSyncStatus() { return syncStatus; }
    public void setSyncStatus(SyncStatus syncStatus) { this.syncStatus = syncStatus; }
}
