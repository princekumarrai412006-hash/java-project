package com.vityarthi.studyhub.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private TaskStatus status;
    @Column(nullable = false) private LocalDate dueDate;
    private int estimatedMinutes;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id") private Course course;

    protected Task() { }
    public Task(String title, LocalDate dueDate, int estimatedMinutes) {
        if (title == null || title.isBlank() || dueDate == null || estimatedMinutes <= 0) throw new IllegalArgumentException("Task details are invalid");
        this.title = title.trim(); this.dueDate = dueDate; this.estimatedMinutes = estimatedMinutes; this.status = TaskStatus.PENDING;
    }
    public void markComplete() { status = TaskStatus.COMPLETED; }
    public void setCourse(Course course) { this.course = course; }
    public Long getId() { return id; } public String getTitle() { return title; } public LocalDate getDueDate() { return dueDate; }
    public int getEstimatedMinutes() { return estimatedMinutes; } public TaskStatus getStatus() { return status; } public Course getCourse() { return course; }
    @Override public String toString() { return title + " | due " + dueDate + " | " + status + " | " + estimatedMinutes + " min"; }
}
