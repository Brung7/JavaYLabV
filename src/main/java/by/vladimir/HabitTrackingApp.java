package by.vladimir;

import by.vladimir.migration.LiquibaseMigration;

public class HabitTrackingApp {
    public static void main(String[] args) {
        LiquibaseMigration.runMigration();
    }
}