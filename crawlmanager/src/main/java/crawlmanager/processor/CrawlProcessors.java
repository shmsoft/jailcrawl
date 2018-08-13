package crawlmanager.processor;

import crawlmanager.config.ConfigReader;
import crawlmanager.dbutils.DBUtil;
import crawlmanager.executor.CrawlStatus;
import crawlmanager.executor.ScriptExecutor;
import crawlmanager.models.tables.JobHistory;
import crawlmanager.models.tables.Jobs;
import crawlmanager.models.tables.records.JobHistoryRecord;
import org.jooq.Record;
import org.jooq.Result;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrawlProcessors implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(CrawlProcessors.class.getName());

    @Override
    public void run() {

        if (!DBUtil.JOBS_HISTORY_QUEUE.isEmpty()) {
            Result<JobHistoryRecord> currentJob = DBUtil.JOBS_HISTORY_QUEUE.remove();
            Integer jobHistoryId = currentJob.get(0).getValue(JobHistory.JOB_HISTORY.ID);
            Integer jobId = currentJob.get(0).getValue(JobHistory.JOB_HISTORY.JOBID);
            Record record = DBUtil.JOBS_BY_ID.get(jobId);
            String jobName = record.getValue(Jobs.JOBS.JOBNAME);
            String args = record.getValue(Jobs.JOBS.ARGUMENTS);
            String processName = record.getValue(Jobs.JOBS.PROCESS);
            Integer pauseInMillis = record.getValue(Jobs.JOBS.PAUSE_MILLIS);
            Integer timeoutMillis = record.getValue(Jobs.JOBS.TIMEOUT_MILLIS);
            LOGGER.log(Level.INFO, "Executing job " + jobName);
            String outputDir = ConfigReader.HOME + "/" + ConfigReader.OUTPUT_DIR + "/" + LocalDate.now();
            DBUtil.ensureOutputDirectoryIsPresent(outputDir);
            String[] arguments = {args, outputDir, "" + pauseInMillis};

            //update job status to running
            DBUtil.updateJobsStatus(jobHistoryId, CrawlStatus.RUNNING, "");
            CrawlStatus execute = ScriptExecutor.execute(ConfigReader.HOME + "/" + ConfigReader.SCRIPT_DIR + "/" + processName,
                    processName,
                    arguments,
                    timeoutMillis);

            //update job done
            DBUtil.updateJobsStatus(jobHistoryId, execute, execute.getMessage().get(args));
        }

    }

    private void processRecord(Record record) {
        //update start time of job
        //call script executor
        //update crawl status
        //on complete update job_history and crawl status
    }
}
