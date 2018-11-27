package com.zyjcxc.spring.cloud.weather.service;

import com.zyjcxc.spring.cloud.weather.vo.City;

import java.io.IOException;
import java.util.List;

public interface CityService {

    List<City> getCityList() throws IOException;

}
