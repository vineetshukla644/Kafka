package com.example.springquartzmicroservice;

import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/jobs")
public class SchedularContoller {

    private SchedulerService ser;

    @GetMapping("/start-scheduler")
    public String all() {

        ser = new SchedulerService();

        ser.setup();

        return "Scheduler Started";
    }


    @GetMapping("/schedule-job")
    public String startSchedule() {



        ser.scheduleJob();



        return "Job Scheduled and Running";
    }


    @GetMapping("/manual-trigger")
    public String manualTrigger() {

        ser.triggerJob("group1");

        return "Job Running";
    }

}
