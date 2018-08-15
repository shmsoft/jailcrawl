package crawlmanager;

import crawlmanager.executor.CrawlStatus;
import crawlmanager.executor.ScriptExecutor;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class ScriptExecutorTest {

    private static final String scriptPath = new File("src/test/resources") + "/scripts/java";
    private static final File outputDir = new File("out");

    @BeforeClass
    public static void createOutputDir() {
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
    }

    @AfterClass
    public static void cleanOutputDir() {
        if (outputDir.exists()) {
            outputDir.delete();
        }
    }

    @Test
    public void testExecuteSuccess() throws Exception {
        String[] args = {"NoException", outputDir.getAbsolutePath(), "500"};
        CrawlStatus crawlStatus = ScriptExecutor.execute(scriptPath,
                "java",
                args,
                1000);
        Assert.assertTrue(crawlStatus == CrawlStatus.SUCCESS);
        System.out.println(crawlStatus.getMessage().get(args[0]));
        crawlStatus.getMessage().remove(args[0]);
    }

    @Test
    public void testExecuteFail() throws Exception {
        String[] args = {"WithException", outputDir.getAbsolutePath(), "500"};
        CrawlStatus crawlStatus = ScriptExecutor.execute(scriptPath,
                "java",
                args,
                1000);
        Assert.assertTrue(crawlStatus == CrawlStatus.FAIL);
        System.out.println(crawlStatus.getMessage().get(args[0]));
        crawlStatus.getMessage().remove(args[0]);
    }

    @Test
    public void testExecuteTimeout() throws Exception {
        String[] args = {"WithTimeout", outputDir.getAbsolutePath(), "500"};
        CrawlStatus crawlStatus = ScriptExecutor.execute(scriptPath,
                "java",
                args,
                1000);
        Assert.assertTrue(crawlStatus == CrawlStatus.TIMEOUT);
        System.out.println(crawlStatus.getMessage().get(args[0]));
        crawlStatus.getMessage().remove(args[0]);
    }

    @Test
    public void testExecuteWithArgs() throws Exception {
        String[] args = {"NoExceptionWithArgs", outputDir.getAbsolutePath(), "500"};
        CrawlStatus crawlStatus = ScriptExecutor.execute(scriptPath,
                "java",
                args,
                1000);
        Assert.assertTrue(crawlStatus == CrawlStatus.SUCCESS);
        System.out.println(crawlStatus.getMessage().get(args[0]));
        crawlStatus.getMessage().remove(args[0]);
    }
}