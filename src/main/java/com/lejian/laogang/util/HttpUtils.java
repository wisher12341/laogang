package com.lejian.laogang.util;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.lejian.laogang.common.ComponentRespCode.REMOTE_ERROR;

public class HttpUtils {

    public static String get(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(url,String.class);
        }catch (HttpClientErrorException e){
            REMOTE_ERROR.doThrowException("fail to get "+url);
        }
        return null;
    }
}
