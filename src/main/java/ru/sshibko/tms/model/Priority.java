package ru.sshibko.tms.model;

public enum Priority {
    HIGH ("HIGH"),
    MEDIUM ("MEDIUM"),
    LOW ("LOW");

    private final String name;

    Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
