package first_cron_job;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by sousaJ on 16/10/2020
 * in package - first_cron_job
 **/
@Slf4j
@Builder
public class ScheduleJob {
    private Scheduler scheduler;
    private String cron;
    private String group;
    private String triggerName;
    private JobDataMap jobDataMap;
    private String jobName;
    private Class<? extends Job> classJob;

    public Scheduler invoke() throws SchedulerException {
        JobDataMap jobDataMap = this.jobDataMap;

        JobDetail jobDetail = newJob(ScanDirectoryJob.class)
                .withIdentity((jobName + "-Trigger"), Scheduler.DEFAULT_GROUP)
                .ofType(classJob)
                .setJobData(jobDataMap)
                .build();

        Trigger cronTrigger = newTrigger()
                .withIdentity(triggerName,group)
                .startNow()
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cron))
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);
        return this.scheduler;
    }

}
