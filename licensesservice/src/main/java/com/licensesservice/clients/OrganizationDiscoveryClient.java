package com.licensesservice.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.licensesservice.model.Organization;
import com.licensesservice.model.OrganizationServiceRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;

@Component
public class OrganizationDiscoveryClient {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public Organization getOrganization(String organizationId) {
		RestTemplate restTemplate = new RestTemplate();
		
		List<ServiceInstance> instances = discoveryClient.getInstances("ORGANIZATIONSERVICE");
		
		if (null == instances || instances.size() == 0 )
			return null;
		
		String serviceUri = String.format("%s/organization/getname", instances.get(0).getUri().toString());
		
		OrganizationServiceRequest organizationServiceRequest = new OrganizationServiceRequest();
		organizationServiceRequest.setOrgId(Integer.parseInt(organizationId));
		
		ResponseEntity<Organization> restExchange = restTemplate.postForEntity(serviceUri, organizationServiceRequest,
				Organization.class);
		
		return restExchange.getBody();
	}

}
