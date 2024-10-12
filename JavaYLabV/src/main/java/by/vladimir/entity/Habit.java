package by.vladimir.entity;

import java.util.Date;
import java.util.List;

public class Habit {
    private String name;
    //Даты выполнения привычки
    private List<Date> completion;
    private String description;
    private Frequency frequency;

    public Habit(String name, List<Date> completion, String description, Frequency frequency) {
        this.name = name;
        this.completion = completion;
        this.description = description;
        this.frequency = frequency;
    }

    public Habit(String name, String description, Frequency frequency) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
    }

    public Habit(String name, List<Date> completion, String description) {
        this.name = name;
        this.completion = completion;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Date> getCompletion() {
        return completion;
    }

    public void setCompletion(List<Date> completion) {
        this.completion = completion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", completion=" + completion +
                ", description='" + description + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
