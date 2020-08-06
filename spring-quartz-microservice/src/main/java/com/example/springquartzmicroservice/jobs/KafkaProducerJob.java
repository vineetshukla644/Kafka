package com.example.springquartzmicroservice.jobs;

import com.example.springquartzmicroservice.ScheduleTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KafkaProducerJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        LOG.info("The current time is: " + dateFormat.format(new Date()));


    }
}
