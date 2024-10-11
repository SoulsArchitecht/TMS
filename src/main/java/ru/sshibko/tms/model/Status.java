package ru.sshibko.tms.model;

public enum Status {
    PENDING ("PENDING"),
    IN_PROGRESS ("IN_PROGRESS"),
    COMPLETED ("COMPLETED");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
