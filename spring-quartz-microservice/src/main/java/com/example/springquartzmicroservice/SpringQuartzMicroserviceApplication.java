package com.example.springquartzmicroservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringQuartzMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringQuartzMicroserviceApplication.class, args);
	}
/*
	@Bean
	public JobDetail scheduleJob() {
		return JobBuilder.newJob(ScheduleTask.class).storeDurably().withIdentity("sample_schedule")
				.withDescription("Sample schedule task").build();
	}


	@Bean
	public Trigger scheduleTrigger() {
		return newTrigger().withIdentity("trigger").forJob(scheduleJob()).withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
				.build();
	}


*/







}
