package org.panacea.drmp.ree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.query.input.QueryInput;
import org.panacea.drmp.ree.domain.query.output.QueryLikelihood;
import org.panacea.drmp.ree.domain.serviceLevel.ServiceLevelImpact;
import org.panacea.drmp.ree.service.REEInputRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class REEInputRequestServiceImpl implements REEInputRequestService {
    @Autowired
    private RestTemplate restTemplate;

//    @Value("${businessEntityInventory.endpoint}")
//    private String businessEntityInventoryURL;
//    @Value("${businessEntityInventory.fn}")
//    private String businessEntityInventoryFn;

//    @Value("${serviceLevelInventory.endpoint}")
//    private String serviceLevelInventoryURL;
//    @Value("${serviceLevelInventory.fn}")
//    private String serviceLevelInventoryFn;

//    @Value("${businessNetworkMapping.endpoint}")
//    private String businessNetworkMappingURL;
//    @Value("${businessNetworkMapping.fn}")
//    private String businessNetworkMappingFn;

    @Value("${queryInput.endpoint}")
    private String queryInputURL;
    @Value("${queryOutput.endpoint}")
    private String queryOutput;

//    @Value("${configurationSpecification.endpoint}")
//    private String configurationSpecificationURL;
//    @Value("${configurationSpecification.fn}")
//    private String configurationSpecificationFn;


//    @Override
//    public BusinessEntityInventory getBusinessEntityInventoryFile(String snapshotId) {
//        ResponseEntity<BusinessEntityInventory> responseEntity = restTemplate.exchange(
//                businessEntityInventoryURL + '/' + snapshotId + '/' + businessEntityInventoryFn,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<BusinessEntityInventory>() {
//                });
//        BusinessEntityInventory businessEntityInventory = responseEntity.getBody();
//
//        return businessEntityInventory;
//    }
//
//    @Override
//    public ServiceLevelInventory getServiceLevelInventoryFile(String snapshotId) {
//        ResponseEntity<ServiceLevelInventory> responseEntity = restTemplate.exchange(
//                serviceLevelInventoryURL + '/' + snapshotId + '/' + serviceLevelInventoryFn,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<ServiceLevelInventory>() {
//                });
//        ServiceLevelInventory serviceLevelInventory = responseEntity.getBody();
//
//        return serviceLevelInventory;
//    }
//
//    @Override
//    public BusinessNetworkMapping getBusinessNetworkMappingFile(String snapshotId) {
//        ResponseEntity<BusinessNetworkMapping> responseEntity = restTemplate.exchange(
//                businessNetworkMappingURL + '/' + snapshotId + '/' + businessNetworkMappingFn,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<BusinessNetworkMapping>() {
//                });
//        BusinessNetworkMapping businessNetworkMapping = responseEntity.getBody();
//
//        return businessNetworkMapping;
//    }

    @Override
    public QueryLikelihood getLikelihoodQueryFile(DataNotification notification) {
        ResponseEntity<QueryLikelihood> responseEntity = restTemplate.exchange(
                queryOutput + '/' + notification.getSnapshotId() + '/' + notification.getQueryId() + "/likelihood",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QueryLikelihood>() {
                });
        QueryLikelihood queryLikelihood = responseEntity.getBody();

        return queryLikelihood;
    }

    @Override
    public QueryInput getQueryInputFile(DataNotification notification) {
        ResponseEntity<QueryInput> responseEntity = restTemplate.exchange(
                queryInputURL + '/' + notification.getSnapshotId() + '/' + notification.getQueryId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QueryInput>() {
                });
        QueryInput queryInput = responseEntity.getBody();

        return queryInput;
    }

    @Override
    public List<ServiceLevelImpact> getImpactQueryFile(DataNotification notification) {
        ResponseEntity<List<ServiceLevelImpact>> responseEntity = restTemplate.exchange(
                queryOutput + '/' + notification.getSnapshotId() + '/' + notification.getQueryId() + "/impact",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ServiceLevelImpact>>() {
                });
        List<ServiceLevelImpact> queryImpact = responseEntity.getBody();

        return queryImpact;
    }

//    @Override
//    public ConfigurationSpecification getConfigurationSpecificationFile(String snapshotId) {
//        ResponseEntity<ConfigurationSpecification> responseEntity = restTemplate.exchange(
//                configurationSpecificationURL + '/' + snapshotId + '/' + configurationSpecificationFn,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<ConfigurationSpecification>() {
//                });
//        ConfigurationSpecification configurationSpecification = responseEntity.getBody();
//
//        return configurationSpecification;
//    }
}
