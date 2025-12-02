package lidar.application.monitor;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import lidar.client.domain.LIDAR;
import lidar.infrastructure.MemoryHistoryRepo;
import lidar.infrastructure.OpcUaConnector;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PollingMonitor {
    private final OpcUaConnector connector;
    private final Map<String, String> attrMap;
    private final LIDAR lidar;
    private final MemoryHistoryRepo history;
    private final double intervalSeconds;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ScheduledExecutorService scheduler;

    public PollingMonitor(OpcUaConnector connector, Map<String, String> attrMap,
            LIDAR lidar, MemoryHistoryRepo history, double intervalSeconds) {
        this.connector = connector;
        this.attrMap = attrMap;
        this.lidar = lidar;
        this.history = history;
        this.intervalSeconds = Math.max(0.001, intervalSeconds);
    }

    public void start() {
        running.set(true);
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            if (!running.get())
                return;

            try {
                List<String> nodeIds = List.copyOf(attrMap.values());
                List<DataValue> values = connector.readNodes(nodeIds);
                Instant ts = Instant.now();

                int i = 0;
                for (Map.Entry<String, String> entry : attrMap.entrySet()) {
                    if (i < values.size()) {
                        DataValue dv = values.get(i);
                        if (dv.getValue().isNotNull()) {
                            Object val = dv.getValue().getValue();
                            String attr = entry.getKey();
                            lidar.setAttr(attr, val);
                            history.append(attr, val, ts);
                        }
                    }
                    i++;
                }
            } catch (Exception e) {
                // Silence connection errors - the reconnection system will handle them
                // Do not show errors to keep output clean
            }
        }, 0, (long) (intervalSeconds * 1000), TimeUnit.MILLISECONDS);
    }

    public void stop() {
        running.set(false);
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}