package com.exemplo.model;

public class Notification {
    private String message;
    private String priority; // "baixa" ou "alta"

    // Construtor padrão
    public Notification() {
    }

    // Construtor com parâmetros
    public Notification(String message, String priority) {
        this.message = message;
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Notification{" +
                message + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
