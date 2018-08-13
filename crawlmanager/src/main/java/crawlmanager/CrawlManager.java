package crawlmanager;

import crawlmanager.config.ConfigReader;
import crawlmanager.dbutils.DBUtil;
import crawlmanager.processor.CrawlProcessors;

import java.util.stream.IntStream;

public class CrawlManager {

    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors() * 10;

    public static void main(String[] args) {
        //test script path is available
        ConfigReader.testScriptPath();

        //then test db connection
        DBUtil.testConnection();

        //get all jobs and make entry in jobs_history table with state READY
        DBUtil.makeAllActiveJobsReadyForExecution();

        //each processor threads
        IntStream.range(0, MAX_THREADS).forEach(i -> {
            new Thread(new CrawlProcessors()).start();
        });
    }
}
