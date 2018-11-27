package com.zyjcxc.spring.cloud.weather.job;

import com.zyjcxc.spring.cloud.weather.service.CityService;
import com.zyjcxc.spring.cloud.weather.service.WeatherDataService;
import com.zyjcxc.spring.cloud.weather.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeathSyncDataJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(WeathSyncDataJob.class);

    @Autowired
    private CityService cityService;
    @Autowired
    private WeatherDataService weatherDataService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Weather Data Sync Job. Start！");
        // 获取城市ID列表
        List<City> cityList = new ArrayList<>();

        try {
            cityList = Optional.ofNullable(cityService.getCityList()).orElseGet(ArrayList::new);
        } catch (Exception e) {
            logger.error("Exception!", e);
        }

        if (cityList.size() == 0) {
            logger.info("Weather Data is Empty！");
            return;
        }
        int count = 0;
        for (City city : cityList) {
            if (!StringUtils.isEmpty(city.getCity_code())) {
                weatherDataService.getDataByCityId(city.getCity_code());
                logger.info("Weather Data Sync Job, cityId:" + city.getCity_code());
                count ++;
                if (count == 10) {
                    break;
                }
            }
        }
        logger.info("Weather Data Sync Job. End！");
    }
}
