package com.example.Task.Api.dto;

public class SyncResponseItem {
    private String id;
    private boolean success;
    private String message;
    private String serverTask;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getServerTask() {
        return serverTask;
    }
    public void setServerTask(String serverTask) {
        this.serverTask = serverTask;
    }

    public SyncResponseItem() {}

    public SyncResponseItem(String id, boolean success, String message, String serverTask) {
        this.id = id;
        this.success = success;
        this.message = message;
        this.serverTask = serverTask;
    }

    // ✅ Proper builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private boolean success;
        private String message;
        private String serverTask;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder serverTask(String serverTask) {
            this.serverTask = serverTask;
            return this;
        }

        public SyncResponseItem build() {
            return new SyncResponseItem(id, success, message, serverTask);
        }
    }
}
