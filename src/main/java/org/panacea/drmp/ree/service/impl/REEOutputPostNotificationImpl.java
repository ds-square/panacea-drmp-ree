package org.panacea.drmp.ree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.notification.DataNotificationResponse;
import org.panacea.drmp.ree.service.REEOutputPostNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Service
public class REEOutputPostNotificationImpl implements REEOutputPostNotification {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${queryOutput.endpoint}")
    private String queryOutputURL;

    @Value("${bia.endpoint}")
    private String biaURL;

    @Value("${lae.endpoint}")
    private String laeURL;


    @Override
    public DataNotificationResponse postLikelihoodRequest(DataNotification notification) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<DataNotification> requestEntity = new HttpEntity<>(notification, requestHeaders);
        ResponseEntity<DataNotificationResponse> responseEntity = restTemplate.exchange(
                laeURL,
                HttpMethod.POST,
                requestEntity,
                DataNotificationResponse.class
        );

        DataNotificationResponse response = responseEntity.getBody();
        return response;
    }

    @Override
    public DataNotificationResponse postImpactRequest(DataNotification notification) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<DataNotification> requestEntity = new HttpEntity<>(notification, requestHeaders);
        ResponseEntity<DataNotificationResponse> responseEntity = restTemplate.exchange(
                biaURL,
                HttpMethod.POST,
                requestEntity,
                DataNotificationResponse.class
        );

        DataNotificationResponse response = responseEntity.getBody();
        return response;
    }
}
