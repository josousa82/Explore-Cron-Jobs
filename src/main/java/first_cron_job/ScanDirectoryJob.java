package first_cron_job;


//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.Objects;

/**
 * Created by sousaJ on 15/10/2020
 * in package - first_cron_job
 **/
@Slf4j
public class ScanDirectoryJob implements Job {
    //    static Logger log = LogManager.getLogger(ScanDirectoryJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        String jobName = jobDetail.getDescription();
        log.warn(jobName + " fired up " + new Date());

        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        String dirName = jobDataMap.getString("SCAN_DIR");
        String fileType = jobDataMap.getString("FILE_EXT");

        log.warn("----------------DIRECTORY NAME " + dirName);

        try {
            String previousTime = jobExecutionContext.getPreviousFireTime().toString();
            log.warn("--------> Previous Fire time" + previousTime);
        }catch (Exception e){
            log.warn("--------> Previous Fire time not set yet");
        }


        log.warn("--------> Next Fire time" + jobExecutionContext.getNextFireTime().toString());

        if (Objects.isNull(dirName)) throw new JobExecutionException("Directory not configured");

        File dir = new File(dirName);

        if (!dir.exists()) throw new JobExecutionException("Invalid dir " + dirName);

        FileFilter fileFilter = new FileExtensionFileFilter(fileType);

        File[] files = dir.listFiles(fileFilter);

        if (Objects.isNull(files) || files.length <= 0) {
            log.warn("No files where found");
            return;
        }
        for (File file : files) {
            File file1 = file.getAbsoluteFile();
            long fileSize = file.length();
            String msg = file1 + " - Size " + fileSize;
            log.warn(msg);
        }
    }
}

