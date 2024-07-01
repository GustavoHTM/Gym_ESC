package com.udesc.gymesc.enums;

public enum TrainingLevel {
    SELECT("Selecione uma opção"),
    BASIC("Iniciante"),
    INTERMEDIARY("Intermediário"),
    ADVANCED("Avançado"),
    SPECIALIST("Especialista");

    private final String displayName;

    TrainingLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}