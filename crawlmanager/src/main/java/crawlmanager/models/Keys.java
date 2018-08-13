/*
 * This file is generated by jOOQ.
 */
package crawlmanager.models;


import crawlmanager.models.tables.JobHistory;
import crawlmanager.models.tables.Jobs;
import crawlmanager.models.tables.records.JobHistoryRecord;
import crawlmanager.models.tables.records.JobsRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>crawl_jobs</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<JobsRecord, Integer> IDENTITY_JOBS = Identities0.IDENTITY_JOBS;
    public static final Identity<JobHistoryRecord, Integer> IDENTITY_JOB_HISTORY = Identities0.IDENTITY_JOB_HISTORY;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<JobsRecord> KEY_JOBS_PRIMARY = UniqueKeys0.KEY_JOBS_PRIMARY;
    public static final UniqueKey<JobHistoryRecord> KEY_JOB_HISTORY_PRIMARY = UniqueKeys0.KEY_JOB_HISTORY_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<JobHistoryRecord, JobsRecord> JOB_HISTORY_IBFK_1 = ForeignKeys0.JOB_HISTORY_IBFK_1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<JobsRecord, Integer> IDENTITY_JOBS = Internal.createIdentity(Jobs.JOBS, Jobs.JOBS.ID);
        public static Identity<JobHistoryRecord, Integer> IDENTITY_JOB_HISTORY = Internal.createIdentity(JobHistory.JOB_HISTORY, JobHistory.JOB_HISTORY.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<JobsRecord> KEY_JOBS_PRIMARY = Internal.createUniqueKey(Jobs.JOBS, "KEY_jobs_PRIMARY", Jobs.JOBS.ID);
        public static final UniqueKey<JobHistoryRecord> KEY_JOB_HISTORY_PRIMARY = Internal.createUniqueKey(JobHistory.JOB_HISTORY, "KEY_job_history_PRIMARY", JobHistory.JOB_HISTORY.ID);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<JobHistoryRecord, JobsRecord> JOB_HISTORY_IBFK_1 = Internal.createForeignKey(crawlmanager.models.Keys.KEY_JOBS_PRIMARY, JobHistory.JOB_HISTORY, "job_history_ibfk_1", JobHistory.JOB_HISTORY.JOBID);
    }
}