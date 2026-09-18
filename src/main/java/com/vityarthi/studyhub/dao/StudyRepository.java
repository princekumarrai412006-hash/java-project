package com.vityarthi.studyhub.dao;

import com.vityarthi.studyhub.model.*;
import com.vityarthi.studyhub.service.StudyHubException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class StudyRepository {
    public Course saveCourse(Course course) {
        String sql = "INSERT INTO courses(code,name,credits) VALUES(?,?,?)";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, course.getCode()); p.setString(2, course.getName()); p.setInt(3, course.getCredits()); p.executeUpdate(); return course;
        } catch (SQLException e) { throw new StudyHubException("Course could not be saved", e); }
    }
    public Task saveTask(long courseId, Task task) {
        String sql = "INSERT INTO tasks(course_id,title,due_date,estimated_minutes,status) VALUES(?,?,?,?,?)";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, courseId); p.setString(2, task.getTitle()); p.setDate(3, java.sql.Date.valueOf(task.getDueDate())); p.setInt(4, task.getEstimatedMinutes()); p.setString(5, task.getStatus().name()); p.executeUpdate(); return task;
        } catch (SQLException e) { throw new StudyHubException("Task could not be saved", e); }
    }
    public List<String> findCourses() {
        List<String> result = new ArrayList<>();
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement("SELECT id,code,name,credits FROM courses ORDER BY code"); ResultSet r = p.executeQuery()) {
            while (r.next()) result.add("#" + r.getLong("id") + " " + r.getString("code") + " - " + r.getString("name") + " (" + r.getInt("credits") + " credits)");
            return result;
        } catch (SQLException e) { throw new StudyHubException("Courses could not be read", e); }
    }
    public List<Task> findTasks() {
        List<Task> result = new ArrayList<>();
        String sql = "SELECT title,due_date,estimated_minutes,status FROM tasks ORDER BY due_date";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql); ResultSet r = p.executeQuery()) {
            while (r.next()) { Task t = new Task(r.getString("title"), r.getDate("due_date").toLocalDate(), r.getInt("estimated_minutes")); if ("COMPLETED".equals(r.getString("status"))) t.markComplete(); result.add(t); }
            return result;
        } catch (SQLException e) { throw new StudyHubException("Tasks could not be read", e); }
    }
    public void markTaskComplete(String title) {
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement("UPDATE tasks SET status='COMPLETED' WHERE title=?")) { p.setString(1, title); if (p.executeUpdate() == 0) throw new StudyHubException("No task found with title: " + title); }
        catch (SQLException e) { throw new StudyHubException("Task could not be updated", e); }
    }
    public void deleteCourse(long id) {
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement("DELETE FROM courses WHERE id=?")) { p.setLong(1, id); p.executeUpdate(); }
        catch (SQLException e) { throw new StudyHubException("Course could not be deleted", e); }
    }
}
