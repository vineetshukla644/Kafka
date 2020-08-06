package com.example.springquartzmicroservice;

import com.example.springquartzmicroservice.jobs.KafkaProducerJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class SchedulerService {

   private  SchedulerFactory schedFact;

    private  Scheduler sched;

    public void setup()
    {
         this.schedFact = new org.quartz.impl.StdSchedulerFactory();


        try {
            sched = schedFact.getScheduler();
            sched.start();
            System.out.println(sched.getSchedulerName());


        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

       public void scheduleJob()

       {

           try {

               sched.scheduleJob(defineJob(),defineTrigger());

           } catch (SchedulerException e) {
               e.printStackTrace();
           }
       }


    public void triggerJob(String groupName)

    {

        try {



            for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();

                sched.triggerJob(jobKey);


                System.out.println( "jobkey  : " + jobKey + " "  + jobName + " " + jobGroup );
            }




        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }





    public JobDetail defineJob()
    {
        JobDetail job = newJob(KafkaProducerJob.class)
                .withIdentity("myJob", "group1")
                .build();

        return job;
    }

    public Trigger defineTrigger()
    {
        Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .build();

        return trigger;
    }


    public void getJobKeys() {
        try {
            for (String groupName : sched.getJobGroupNames()) {

                // get jobkey
                for (JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    System.out.println( "jobkey : " + jobKey + " "  + jobName + " " + jobGroup );
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }





}
