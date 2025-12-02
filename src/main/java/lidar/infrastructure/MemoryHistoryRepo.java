package lidar.infrastructure;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryHistoryRepo {
    private final Map<String, Deque<HistoryEntry>> store = new ConcurrentHashMap<>();
    private final long retentionMinutes;

    public MemoryHistoryRepo(long retentionMinutes) {
        this.retentionMinutes = retentionMinutes;
    }

    public void append(String attr, Object value, Instant timestamp) {
        Deque<HistoryEntry> queue = store.computeIfAbsent(attr, k -> new ArrayDeque<>());
        queue.add(new HistoryEntry(timestamp, value));
        prune(attr);
    }

    private void prune(String attr) {
        Instant cutoff = Instant.now().minus(retentionMinutes, ChronoUnit.MINUTES);
        Deque<HistoryEntry> queue = store.get(attr);
        if (queue != null) {
            while (!queue.isEmpty() && queue.peekFirst().timestamp.isBefore(cutoff)) {
                queue.removeFirst();
            }
        }
    }

    public List<HistoryEntry> getHistory(String attr) {
        prune(attr);
        Deque<HistoryEntry> queue = store.get(attr);
        return queue != null ? new ArrayList<>(queue) : Collections.emptyList();
    }

    public static class HistoryEntry {
        public final Instant timestamp;
        public final Object value;

        public HistoryEntry(Instant timestamp, Object value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }
}