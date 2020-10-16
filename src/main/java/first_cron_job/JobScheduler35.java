package first_cron_job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by sousaJ on 16/10/2020
 * in package - first_cron_job
 **/
@Slf4j
public class JobScheduler35 {

    public static void main(String[] args) {
        JobScheduler35 example = new JobScheduler35();

        try {
            Scheduler scheduler = example.createScheduler();
            example.scheduleJob(scheduler, "0 0/1 * 1/1 * ? *");

            scheduler.start();

            log.info("Scheduler started at " + new Date());

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public Scheduler createScheduler() throws Exception{
        return StdSchedulerFactory.getDefaultScheduler();
    }

    // create and schedule a scan directory job with Scheduler

    private void scheduleJob(Scheduler scheduler, String cron) throws SchedulerException {

        // create a JobDetail for the Job
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("SCAN_DIR", "./");


        JobDetail jobDetail = newJob(ScanDirectoryJob.class)
                .withIdentity("ScanDirectory", "jobGroup")
                .setJobData(jobDataMap)
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                .withIntervalInSeconds(40)
                .repeatForever())
                .build();

        Trigger cronTrigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder
                         .cronSchedule(cron))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
