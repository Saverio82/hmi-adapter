package com.hitachirail.maas.hmiadapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachirail.maas.hmiadapter.businessentity.TenantManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class MonitoringCoreClient {

    private static final String GET_TENANT_MANAGEMENT="/registry/tenant-management";

    @Value(value = "${http.monitoringcore.base.url}")
    String serverEndpoint;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public MonitoringCoreClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    private String getInvocationUrl(String serviceUrl){

        String invocationUrl = serverEndpoint;

        if(!invocationUrl.endsWith("/")){
            invocationUrl += "/";
        }

        return invocationUrl + serviceUrl;

    }

    public List<TenantManagement> getTenantManagement(){
        log.debug("invoking url "+getInvocationUrl(GET_TENANT_MANAGEMENT) );
        ResponseEntity<List<TenantManagement>> response =  restTemplate.exchange(getInvocationUrl(GET_TENANT_MANAGEMENT),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TenantManagement>>() {
                });

        return response.getBody();
    }
}
