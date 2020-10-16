package first_cron_job;

import com.cronutils.model.CronType;
import com.cronutils.validation.Cron;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by sousaJ on 16/10/2020
 * in package - first_cron_job
 **/
@Slf4j
public class JobScheduler35 {

    @Cron(type = CronType.QUARTZ, message = "Not a valid Cron")
    public static String cron;

    public static void main(String[] args) throws Exception {
        customInputScheduler();

        JobDataMap xmlData = generateScanDirectoryDataMap("./src/main/resources/", FileExtensionEnum.XML.getFileExtension());
        JobDataMap txtData = generateScanDirectoryDataMap("./src/main/resources/", FileExtensionEnum.TXT.getFileExtension());

        try {
            Scheduler scheduler = JobScheduler35.createScheduler();
            scheduler.start();

            JobScheduler35.scheduleJob(scheduler, "0 0/1 * 1/1 * ? *", "group1", "txtTrigger", "txtJob", txtData, ScanDirectoryJob.class);
            JobScheduler35.scheduleJob(scheduler, "0 0 0/1 1/1 * ? *", "group2", "xmlTrigger", "xmlJob", xmlData, ScanDirectoryJob.class);


            log.warn("Scheduler started at " + new Date());

        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    private static void customInputScheduler() throws Exception {
        System.out.println("Would you like to schedule a job? y/n");
        Scanner scannerJ = new Scanner(System.in);
        boolean flag = scannerJ.nextLine().toLowerCase().equals("y");

        if (flag) {
            System.out.println("Enter a dir to scan: ");
            Scanner scanner = new Scanner(System.in);
            String dir = scanner.nextLine();
            String fileExt = getFileExtension();
            JobDataMap customRuntimeDataMap = generateScanDirectoryDataMap(dir, fileExt);

            System.out.println("Enter a cron expression: ");
            Scanner scannerCron = new Scanner(System.in);
            cron = scannerCron.nextLine();
            try {
                Scheduler customScheduler = JobScheduler35.createScheduler();
                JobScheduler35.scheduleJob(customScheduler, cron, "group1", "customTrigger", "customJob", customRuntimeDataMap, ScanDirectoryJob.class);
                customScheduler.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }else {
            System.exit(1);
        }
    }

    private static String getFileExtension() {
        String fileExt;
        boolean valid;
        do {
            System.out.println("Enter the file extension to search: please press q to quit :txt or xml : ");
            Scanner scannerFileExt = new Scanner(System.in);
            fileExt = scannerFileExt.nextLine();
            valid = Objects.equals(".".concat(fileExt.toLowerCase()), FileExtensionEnum.XML.getFileExtension()) || Objects.equals(".".concat(fileExt.toLowerCase()), FileExtensionEnum.TXT.getFileExtension());
            if (!valid) System.out.print("Invalid Extension.");
            if (fileExt.toLowerCase().equals("q")) System.exit(1);
        } while (!valid);
        return fileExt;
    }

    private static JobDataMap generateScanDirectoryDataMap(String scanDir, String fileExtensionEnum) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("SCAN_DIR", scanDir);
        jobDataMap.put("FILE_EXT", fileExtensionEnum);
        return jobDataMap;
    }

    public static Scheduler createScheduler() throws Exception {
        return StdSchedulerFactory.getDefaultScheduler();
    }

    private static Scheduler scheduleJob(Scheduler scheduler, String cron, String group, String triggerName, String jobName, JobDataMap jobDataMap, Class<? extends Job> classJob) throws SchedulerException {

        return ScheduleJob.builder().scheduler(scheduler).cron(cron).group(group).triggerName(triggerName).jobName(jobName).jobDataMap(jobDataMap).classJob(classJob).build().invoke();
    }


}
