package com.zyjcxc.spring.cloud.weather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyjcxc.spring.cloud.weather.utils.JsonUtils;
import com.zyjcxc.spring.cloud.weather.vo.City;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Override
    public List<City> getCityList() throws IOException {

        JsonUtils utils = new JsonUtils();
        String jsonStr = utils.jsonFromFile();
        System.out.println(jsonStr);

        List<City> cityList = objectMapper.readValue(jsonStr, new TypeReference<List<City>>() {});

        return cityList;
    }
}
