package crawlmanager.executor;

import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.buildobjects.process.TimeoutException;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Assuming to call like
 * process Arguments
 * ex - java Crawl result 2500
 * 3rd argument is output directory
 * 4rth argument is random pause in milliseconds, between subsequent requests
 */
public class ScriptExecutor {

    private static final Logger LOGGER = Logger.getLogger(ScriptExecutor.class.getName());
    public static CrawlStatus execute(String scriptPath, String processName, String[] args, long timeoutMillis) {

        if (Objects.isNull(args) || args.length == 0) {
            throw new IllegalArgumentException("args can not be null or empty");
        }

        CrawlStatus crawlStatus = validate(args[0], scriptPath, processName, timeoutMillis);
        if (crawlStatus == CrawlStatus.FAIL) {
            return crawlStatus;
        }

        ProcBuilder procBuilder = new ProcBuilder(processName)
                .withArgs(args)
                .withTimeoutMillis(timeoutMillis)
                .withWorkingDirectory(new File(scriptPath));
        try {
            ProcResult result = procBuilder.run();
            LOGGER.info(result.getOutputString());
            CrawlStatus.SUCCESS.getMessage().put(args[0], "completed crawl in " + result.getExecutionTime() + " ms");
            return CrawlStatus.SUCCESS;
        } catch (TimeoutException ex) {
            CrawlStatus.TIMEOUT.getMessage().put(args[0], ex.getMessage());
            return CrawlStatus.TIMEOUT;
        } catch (Exception ex) {
            CrawlStatus.FAIL.getMessage().put(args[0], ex.getMessage());
            return CrawlStatus.FAIL;
        }
    }

    private static CrawlStatus validate(String arg, String scriptPath, String processName, long timeoutMillis) {
        String reason = null;
        if (Objects.isNull(scriptPath) || scriptPath.isEmpty()) {
            reason = "scriptPath can not be null or empty";
        }
        if (Objects.isNull(processName) || processName.isEmpty()) {
            reason = "processName can not be null or empty";
        }
        if (timeoutMillis < 0) {
            reason = "timeout value can not be negative";
        }
        if (Objects.nonNull(reason)) {
            CrawlStatus.FAIL.getMessage().put(arg, reason);
            return CrawlStatus.FAIL;
        }
        return null;
    }
}
