/*
 * This file is generated by jOOQ.
 */
package crawlmanager.models;


import crawlmanager.models.tables.JobHistory;
import crawlmanager.models.tables.Jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CrawlJobs extends SchemaImpl {

    private static final long serialVersionUID = 161960138;

    /**
     * The reference instance of <code>crawl_jobs</code>
     */
    public static final CrawlJobs CRAWL_JOBS = new CrawlJobs();

    /**
     * The table <code>crawl_jobs.jobs</code>.
     */
    public final Jobs JOBS = crawlmanager.models.tables.Jobs.JOBS;

    /**
     * The table <code>crawl_jobs.job_history</code>.
     */
    public final JobHistory JOB_HISTORY = crawlmanager.models.tables.JobHistory.JOB_HISTORY;

    /**
     * No further instances allowed
     */
    private CrawlJobs() {
        super("crawl_jobs", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Jobs.JOBS,
            JobHistory.JOB_HISTORY);
    }
}