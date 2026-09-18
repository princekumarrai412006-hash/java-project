package com.vityarthi.studyhub.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) private String code;
    @Column(nullable = false) private String name;
    private int credits;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Task> tasks = new ArrayList<>();

    protected Course() { }
    public Course(String code, String name, int credits) {
        if (code == null || code.isBlank() || name == null || name.isBlank() || credits <= 0) throw new IllegalArgumentException("Course details are invalid");
        this.code = code.trim().toUpperCase(); this.name = name.trim(); this.credits = credits;
    }
    public void addTask(Task task) { tasks.add(Objects.requireNonNull(task)); task.setCourse(this); }
    public Long getId() { return id; } public String getCode() { return code; } public String getName() { return name; }
    public int getCredits() { return credits; } public List<Task> getTasks() { return Collections.unmodifiableList(tasks); }
    @Override public String toString() { return code + " - " + name + " (" + credits + " credits)"; }
}
