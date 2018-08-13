package crawlmanager.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CrawlStatus {
    READY,
    RUNNING,
    SUCCESS,
    FAIL,
    TIMEOUT;

    private Map<String, String> message = new ConcurrentHashMap<>();

    public Map<String, String> getMessage() {
        return message;
    }
}
