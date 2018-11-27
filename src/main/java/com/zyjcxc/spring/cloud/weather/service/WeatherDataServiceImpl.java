package com.zyjcxc.spring.cloud.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyjcxc.spring.cloud.weather.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author mengqa
 * @create 2018-11-25 22:13
 **/
@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);

    private static final String API_URL = "http://t.weather.sojson.com/api/weather/city/";
    public static final long TIME = 1800L;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String apiUrl = API_URL + cityId;
        WeatherResponse response = new WeatherResponse();
        ResponseEntity<String> responseEntity;

        String strBody = null;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(apiUrl)) {
            strBody = ops.get(apiUrl);
            logger.info("redis has data");
        } else {

            try {
                responseEntity = restTemplate.getForEntity(apiUrl, String.class);
            } catch (HttpClientErrorException he) {
                response.setStatus(404);
                response.setMessage("Request resource not found");
                return response;
            }
            if (responseEntity.getStatusCodeValue() == 200) {
                logger.info("redis no data");
                strBody = responseEntity.getBody();
                ops.set(apiUrl, strBody, TIME, TimeUnit.SECONDS);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response = objectMapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(506);
            return response;
        }

        return response;
    }

    public static void main(String[] args) {

    }
}
