package by.vladimir.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Frequency {
    DAILY,
    WEEKLY,
    MONTHLY;
    public static Optional<Frequency> find(String role){
        return Arrays.stream(values()).filter(it->it.name().equals(role)).findFirst();
    }
}