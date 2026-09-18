package com.vityarthi.studyhub;

import com.vityarthi.studyhub.model.*;
import com.vityarthi.studyhub.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StudyHubTest {
    @Test void taskCanBeCompleted() {
        Task task = new Task("Read interfaces", LocalDate.now().plusDays(2), 30);
        task.markComplete();
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }
    @Test void analyticsCountsTasksAndMinutes() {
        Task first = new Task("A", LocalDate.now(), 20); Task second = new Task("B", LocalDate.now(), 40); second.markComplete();
        var report = new AnalyticsService().summarize(List.of(first, second));
        assertEquals(2, report.get("totalTasks")); assertEquals(1L, report.get("completedTasks")); assertEquals(60L, report.get("plannedMinutes"));
    }
    @Test void invalidCourseIsRejected() { assertThrows(IllegalArgumentException.class, () -> new Course("", "Nope", 3)); }
}
