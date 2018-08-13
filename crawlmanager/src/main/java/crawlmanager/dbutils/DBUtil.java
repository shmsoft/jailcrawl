package crawlmanager.dbutils;

import crawlmanager.config.ConfigReader;
import crawlmanager.executor.CrawlStatus;
import crawlmanager.models.tables.JobHistory;
import crawlmanager.models.tables.Jobs;
import crawlmanager.models.tables.records.JobHistoryRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {

    private static final String USER_NAME = ConfigReader.get("db.username");
    private static final String PASSWORD = ConfigReader.get("db.password");
    private static final String DB_NAME = ConfigReader.get("db.database");
    private static final String URL = ConfigReader.get("db.url") + DB_NAME;
    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
    public static final Queue<Result<JobHistoryRecord>> JOBS_HISTORY_QUEUE = new ConcurrentLinkedQueue<>();
    public static final Map<Integer, Record> JOBS_BY_ID = new HashMap<>();

    public static void makeAllActiveJobsReadyForExecution() {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            DSLContext context = DSL.using(conn, SQLDialect.MYSQL);

            Result<Record> records = context.select().from(Jobs.JOBS).where(Jobs.JOBS.ACTIVE).fetch();
            for (Record record : records) {
                Integer id = record.getValue(Jobs.JOBS.ID);
                JOBS_BY_ID.put(id, record);

                LOGGER.log(Level.INFO, "making job with id " + id + " ready for crawl");
                Result<JobHistoryRecord> result = context.insertInto(JobHistory.JOB_HISTORY)
                        .columns(JobHistory.JOB_HISTORY.JOBID, JobHistory.JOB_HISTORY.CREATED_ON)
                        .values(id, new Timestamp(new Date().getTime()))
                        .returning(JobHistory.JOB_HISTORY.ID, JobHistory.JOB_HISTORY.JOBID)
                        .fetch();
                JOBS_HISTORY_QUEUE.add(result);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    public static void ensureOutputDirectoryIsPresent(String outputDir) {
        File file = new File(outputDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void testConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
            context.execute("select version()");
        } catch (Exception ex) {
            throw new IllegalStateException("db exception while testing connection : " + ex.getMessage());
        }
    }

    public static void updateJobsStatus(Integer jobHistoryId, CrawlStatus status, String message) {
        try (Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            DSLContext context = DSL.using(conn, SQLDialect.MYSQL);

            context.update(JobHistory.JOB_HISTORY)
                    .set(JobHistory.JOB_HISTORY.CRAWL_STATE, status.ordinal())
                    .set(JobHistory.JOB_HISTORY.MESSAGE, message)
                    .set((status == CrawlStatus.RUNNING ? JobHistory.JOB_HISTORY.START_TIME : JobHistory.JOB_HISTORY.END_TIME), new Timestamp(new Date().getTime()))
                    .where(JobHistory.JOB_HISTORY.ID.eq(jobHistoryId))
                    .execute();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}