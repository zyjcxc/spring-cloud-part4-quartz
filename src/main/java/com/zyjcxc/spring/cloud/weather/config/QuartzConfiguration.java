package com.zyjcxc.spring.cloud.weather.config;

import com.zyjcxc.spring.cloud.weather.job.WeathSyncDataJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz Configuration.
 * 
 * @since 1.0.0 2017年11月23日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Configuration
public class QuartzConfiguration {

	private static final int TIME = 60; // 更新频率

	// JobDetail
	@Bean
	public JobDetail weatherDataSyncJobDetail() {
		return JobBuilder.newJob(WeathSyncDataJob.class).withIdentity("weatherDataSyncJob")
		.storeDurably().build();
	}
	
	// Trigger
	@Bean
	public Trigger weatherDataSyncTrigger() {
		
		SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(TIME).repeatForever();
		
		return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
				.withIdentity("weatherDataSyncTrigger").withSchedule(schedBuilder).build();
	}
}
