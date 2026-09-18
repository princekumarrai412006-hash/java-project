package com.vityarthi.studyhub.service;

import com.vityarthi.studyhub.model.*;
import java.util.*;
import java.util.concurrent.*;

public class AnalyticsService {
    public Map<String, Object> summarize(List<Task> tasks) {
        if (tasks == null) throw new IllegalArgumentException("Task list cannot be null");
        try (ExecutorService pool = Executors.newFixedThreadPool(2)) {
            Future<Long> minutes = pool.submit(() -> tasks.stream().mapToLong(Task::getEstimatedMinutes).sum());
            Future<Long> completed = pool.submit(() -> tasks.stream().filter(t -> t.getStatus() == TaskStatus.COMPLETED).count());
            try {
                Map<String, Object> report = new LinkedHashMap<>();
                report.put("totalTasks", tasks.size()); report.put("completedTasks", completed.get()); report.put("pendingTasks", tasks.size() - completed.get()); report.put("plannedMinutes", minutes.get());
                return report;
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new StudyHubException("Analytics interrupted", e); }
            catch (ExecutionException e) { throw new StudyHubException("Analytics failed", e.getCause()); }
        }
    }
}
