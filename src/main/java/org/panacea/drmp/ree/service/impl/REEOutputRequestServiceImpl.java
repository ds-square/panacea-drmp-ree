package org.panacea.drmp.ree.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.HashBasedTable;
import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.service.REEOutputRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class REEOutputRequestServiceImpl implements REEOutputRequestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${queryOutput.endpoint}")
    private String queryOutputURL;


    @Override
    public void postRiskProfile(DataNotification notification, HashBasedTable riskProfile) {
        ResponseEntity<String> response = null;
        ObjectMapper mapper =  JsonMapper.builder().addModule(new GuavaModule()).build();
        try {
            String result = mapper.writeValueAsString(riskProfile);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity
                    = new HttpEntity<>(result, headers);

            String endPointUrl = queryOutputURL + '/' + notification.getSnapshotId() + "/"+ notification.getQueryId() + "/riskProfile";
            log.info("[REE] POST risk profile for query ID \"" + notification.getQueryId() + "\" to http://172.16.100.131:8108/persistence/output");

            RestTemplate restTemplate = new RestTemplate();

            response = restTemplate
                    .postForEntity(endPointUrl, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("Response from storage service: " + response);
            byte[] bytes = e.getResponseBodyAsByteArray();

            //Convert byte[] to String
            String s = new String(bytes);

            log.error(s);
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
