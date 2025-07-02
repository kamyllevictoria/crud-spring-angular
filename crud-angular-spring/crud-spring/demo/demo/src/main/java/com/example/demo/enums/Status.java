package com.example.demo.enums;

public enum Status {
    ACTIVE("Active", 0), INACTIVE("Inactive", 1);

    private String status;
    private int valueStatus;

    private Status(String status, int valueStatus) {
        this.status = status;
        this.valueStatus = valueStatus;
    }

    public String getStatus() {
        return status;
    }

    public int getValueStatus() {
        return valueStatus;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                '}';
    }

    public static Status fromValue(int valueStatus) {
        for (Status s : Status.values()) {
            if (s.valueStatus == valueStatus) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid Status value: " + valueStatus);
    }

    public static Status fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Status text cannot be null or empty.");
        }
        for (Status s : Status.values()) {
            if (s.status.equalsIgnoreCase(text)) {
                return s;
            }
        }
        for (Status s : Status.values()) {
            if (s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in Status enum.");
    }
}
