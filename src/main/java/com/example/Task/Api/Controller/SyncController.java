package com.example.Task.Api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Task.Api.ServiceSync.SyncService;
import com.example.Task.Api.dto.SyncRequestItem;
import com.example.Task.Api.ServiceSync.SyncService.SyncStatusSummary;

@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

   
    @PostMapping
    public ResponseEntity<?> sync(@RequestBody List<SyncRequestItem> queue) {
        return ResponseEntity.ok(syncService.processSync(queue));
    }

   
    @GetMapping("/status")
    public ResponseEntity<SyncStatusSummary> status() {
        return ResponseEntity.ok(syncService.status());
    }
}
