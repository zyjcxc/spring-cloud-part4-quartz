package com.zyjcxc.spring.cloud.weather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtils {

    /**
     * @param args
     */
    public String jsonFromFile() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("json/city.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();

    }
//    private static final ObjectMapper objectMapper = new ObjectMapper()
//            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//    public static void main(String[] args) throws IOException {
//        JsonUtils utils = new JsonUtils();
//        String jsonStr = utils.jsonFromFile();
//        System.out.println(jsonStr);
//
//        List<City> cityList = objectMapper.readValue(jsonStr, new TypeReference<List<City>>() {});
//
//    }
}
