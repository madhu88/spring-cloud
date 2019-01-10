package com.licensesservice.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.licensesservice.model.Organization;
import com.licensesservice.model.OrganizationServiceRequest;

@Component
public class OrganizationRestTemplate {

	@Autowired
	private RestTemplate restTemplate;
	
	public Organization getOrganization(String organizationId) {
		OrganizationServiceRequest organizationServiceRequest = new OrganizationServiceRequest();
		organizationServiceRequest.setOrgId(Integer.parseInt(organizationId));
		ResponseEntity<Organization> restExchange = restTemplate.postForEntity("http://organizationservice/organization/getname"
				, organizationServiceRequest, Organization.class, organizationId);
		return restExchange.getBody();
	}

}
