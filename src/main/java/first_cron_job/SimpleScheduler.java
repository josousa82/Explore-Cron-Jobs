package first_cron_job;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;




/**
 * Created by sousaJ on 15/10/2020
 * in package - first_cron_job
 **/
@Slf4j
public class SimpleScheduler {


    public static void main(String[] args) {
        log.warn("App started");
        SimpleScheduler simpleScheduler = new SimpleScheduler();
        simpleScheduler.startScheduler();

    }

    public void startScheduler(){
        Scheduler scheduler = null;
        try{
            // get Scheduler instance from factory
            scheduler = StdSchedulerFactory.getDefaultScheduler();


            // Start the Scheduler
            scheduler.start();
            log.warn("Scheduler started at " + new Date());

        }catch (SchedulerException e){
            log.warn(e.toString());
        }
//        // standby, do something and then start
//        try{
//            if(!scheduler.isInStandbyMode()) {
//                scheduler.standby();
//            }
//
//            // do something
//            scheduler.start();
//        } catch (SchedulerException e) {
//            log.error(e.toString());
//        }

    }

    public void modifyScheduler(){

    }
}







