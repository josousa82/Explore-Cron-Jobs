package first_cron_job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sousaJ on 15/10/2020
 * in package - first_cron_job
 **/

public class TestJob implements Job {
static Log log = LogFactory.getLog(TestJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        String jobName = jobDetail.getDescription();
        log.info(jobName + " fired up " + new Date());
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String dirName = jobDataMap.getString("SCAN_DIR");

        if(Objects.isNull(dirName)) throw new JobExecutionException("Directory not configured");

        File dir = new File(dirName);

        if(!dir.exists()) throw new JobExecutionException("Invalid dir" + dirName);

        FileFilter fileFilter = new FileExtensionFileFilter(".xml");

        File[] files = dir.listFiles(fileFilter);

        if(Objects.isNull(files) || files.length <= 0) {
            log.info("No files where found");
            return;
        }
        for (File file : files){
            File file1 = file.getAbsoluteFile();
            long fileSize = file.length();
            String msg =  file1 + " - Size " + fileSize;
            log.info(msg);
        }
    }
}

