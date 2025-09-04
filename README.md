 # Task Sync API - Java Backend Implementation  .... Use Java  + Advance Java

This project is a **Java (Spring Boot) based backend API** for managing tasks with **offline-first synchronization support**.  
It provides CRUD operations, sync handling, and conflict resolution for a Task Management System.

---

## 🚀 Features
- Task CRUD operations using REST APIs
- Offline-first **Sync API**
- Conflict resolution using **Last Write Wins (LWW)** strategy
- Database integration with **MySQL**
- API testing with **Postman**
- Built with **Spring Boot + JPA (Hibernate) + Lombok**

---

## 🏗️ Project Structure

src/main/java/com/example/taskapi/
├── entity/ # JPA Entities
│ └── Task.java
├── repository/ # Spring Data JPA Repositories
│ └── TaskRepository.java
├── service/ # Business Logic
│ ├── TaskService.java
│ └── SyncService.java
├── controller/ # REST Controllers
│ ├── TaskController.java
│ └── SyncController.java
└── TaskApiApplication.java # Main entry point


---

## ⚙️ Tech Stack
- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA (Hibernate)**
- **MySQL** (database)
- **Lombok** (boilerplate reduction)
- **Maven** (build tool)
- **Postman** (API testing)
- **Chatgpt** (where we need)
- **Chrome** (When we need)

---

## 🔑 API Endpoints

### 📌 Task APIs
- `GET /api/tasks` → Fetch all tasks
- `GET /api/tasks/{id}` → Fetch single task by ID
- `POST /api/tasks` → Create a new task
- `PUT /api/tasks/{id}` → Update an existing task
- `DELETE /api/tasks/{id}` → Delete task

### 🔄 Sync API
- `POST /api/sync`  
  **Request body:**
  ```json
  {
    "changes": [
      { "id": 1, "title": "New Task", "status": "pending", "updatedAt": "2025-09-04T10:00:00Z" }
    ],
    "lastSyncedAt": "2025-09-01T00:00:00Z"
  }
## Response 
{
  "tasks": [ ...serverTasks ],
  "newLastSyncedAt": "2025-09-04T12:00:00Z"
}
## Conflict Resolution

Conflict Strategy: Last Write Wins (LWW)

Server compares updatedAt timestamps:

If client change is newer → overwrite server record

If server change is newer → keep server record

## Testing with Postman

Import API endpoints into Postman.

Test CRUD operations via /api/tasks.

Test sync endpoint via /api/sync.

Verify conflict resolution by updating the same task on client & server.



## To watch full Video Click the Below Link:

#Description: Due to internet and Wi-Fi connectivity issues, I had to record the project demo video in two parts.
Also, I could not include face recording because of an technical issue.

However, I want to assure you that I have put in my 100% effort while implementing this project.
If given the opportunity, I am confident that I will further enhance my skills and contribute effectively to the growth of the organization.

## Part1:https://youtu.be/HtWgoz5A_94?si=hvMyZbGhoWBGmMBo
## Part2:https://youtu.be/4XFdTrTfOp8?si=c-K52AY5az9rhL-V

