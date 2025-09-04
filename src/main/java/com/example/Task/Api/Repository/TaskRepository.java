package com.example.Task.Api.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Task.Api.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, String> {

   
    List<Task> findByIsDeletedFalseOrderByUpdatedAtDesc();

    long countByIsDeletedFalse();
    long countByIsDeletedTrue();
}
