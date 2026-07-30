package com.victormoraes;

public class AccessLogEntry {
    
    private String userId; // user who made the request
    private String resourceId; // the resource/endpoint accessed (e.g., "/admin/users", "/api/accounts/123")
    private long timestamp; // epoch seconds

    public AccessLogEntry(String userId, String resourceId, long timestamp) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
