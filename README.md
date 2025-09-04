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
#Part1:https://youtu.be/HtWgoz5A_94?si=hvMyZbGhoWBGmMBo
#Part2:https://youtu.be/4XFdTrTfOp8?si=c-K52AY5az9rhL-V



# Backend Interview Challenge - Task Sync API

This is a backend developer interview challenge focused on building a sync-enabled task management API. The challenge evaluates understanding of REST APIs, data synchronization, offline-first architecture, and conflict resolution.






Please read these documents in order:

1. **[📋 Submission Instructions](./docs/SUBMISSION_INSTRUCTIONS.md)** - How to submit your solution (MUST READ)
2. **[📝 Requirements](./docs/REQUIREMENTS.md)** - Detailed challenge requirements and implementation tasks
3. **[🔌 API Specification](./docs/API_SPEC.md)** - Complete API documentation with examples
4. **[🤖 AI Usage Guidelines](./docs/AI_GUIDELINES.md)** - Guidelines for using AI tools during the challenge

**⚠️ Important**: DO NOT create pull requests against this repository. All submissions must be through private forks.

## Challenge Overview

Candidates are expected to implement a backend API that:
- Manages tasks (CRUD operations)
- Supports offline functionality with a sync queue
- Handles conflict resolution when syncing
- Provides robust error handling

## Project Structure

```
backend-interview-challenge/
├── src/
│   ├── db/             # Database setup and configuration
│   ├── models/         # Data models (if needed)
│   ├── services/       # Business logic (TO BE IMPLEMENTED)
│   ├── routes/         # API endpoints (TO BE IMPLEMENTED)
│   ├── middleware/     # Express middleware
│   ├── types/          # TypeScript interfaces
│   └── server.ts       # Express server setup
├── tests/              # Test files
├── docs/               # Documentation
└── package.json        # Dependencies and scripts
```

## Getting Started

### Prerequisites
- Node.js (v18 or higher)
- npm or yarn

### Setup
1. Clone the repository
2. Install dependencies:
   ```bash
   npm install
   ```
3. Copy environment variables:
   ```bash
   cp .env.example .env
   ```
4. Run the development server:
   ```bash
   npm run dev
   ```

### Available Scripts

- `npm run dev` - Start development server with hot reload
- `npm run build` - Build TypeScript to JavaScript
- `npm run start` - Start production server
- `npm test` - Run tests
- `npm run test:ui` - Run tests with UI
- `npm run lint` - Run ESLint
- `npm run typecheck` - Check TypeScript types

## Your Task

### Key Implementation Files

You'll need to implement the following services and routes:

- `src/services/taskService.ts` - Task CRUD operations
- `src/services/syncService.ts` - Sync logic and conflict resolution  
- `src/routes/tasks.ts` - REST API endpoints
- `src/routes/sync.ts` - Sync-related endpoints

### Before Submission

Ensure all of these pass:
```bash
npm test          # All tests must pass
npm run lint      # No linting errors
npm run typecheck # No TypeScript errors
```

### Time Expectation

This challenge is designed to take 2-3 hours to complete.

## License

This project is for interview purposes only.
