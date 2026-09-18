package com.vityarthi.studyhub.app;

import com.vityarthi.studyhub.dao.*;
import com.vityarthi.studyhub.model.*;
import com.vityarthi.studyhub.service.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Database.initialize(); StudyRepository repo = new StudyRepository(); AnalyticsService analytics = new AnalyticsService(); seed(repo);
        System.out.println("\n=== StudyHub: Java Study Planner ===");
        System.out.println("Courses:"); repo.findCourses().forEach(System.out::println);
        System.out.println("\nTasks:"); repo.findTasks().forEach(System.out::println);
        System.out.println("\nAnalytics: " + analytics.summarize(repo.findTasks()));
        if (args.length > 0 && "interactive".equalsIgnoreCase(args[0])) runMenu(repo, analytics); else System.out.println("\nRun with 'interactive' for the CRUD menu.");
    }
    private static void seed(StudyRepository repo) {
        if (!repo.findCourses().isEmpty()) return;
        repo.saveCourse(new Course("CSE2001", "Java Programming", 4));
        repo.saveCourse(new Course("CSE2002", "Database Systems", 3));
        repo.saveTask(1, new Task("Implement JDBC repository", LocalDate.now().plusDays(3), 90));
        repo.saveTask(1, new Task("Revise inheritance and interfaces", LocalDate.now().plusDays(1), 60));
    }
    private static void runMenu(StudyRepository repo, AnalyticsService analytics) {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1.List courses  2.List tasks  3.Complete task  4.Analytics  0.Exit"); String choice = in.nextLine();
                try {
                    switch (choice) {
                        case "1" -> repo.findCourses().forEach(System.out::println);
                        case "2" -> repo.findTasks().forEach(System.out::println);
                        case "3" -> { System.out.print("Task title: "); repo.markTaskComplete(in.nextLine()); System.out.println("Task completed."); }
                        case "4" -> System.out.println(analytics.summarize(repo.findTasks()));
                        case "0" -> { return; }
                        default -> System.out.println("Choose a listed option.");
                    }
                } catch (StudyHubException | IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
            }
        }
    }
}
