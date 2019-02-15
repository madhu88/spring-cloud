package com.licensesservice.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import com.licensesservice.model.Organization;
import com.licensesservice.model.OrganizationServiceRequest;

@Component
public class OrganizationOAuth2RestTemplate {

	/*@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	public Organization getOrganization(String organizationId) {		
		OrganizationServiceRequest organizationServiceRequest = new OrganizationServiceRequest();
		organizationServiceRequest.setOrgId(Integer.parseInt(organizationId));
		ResponseEntity<Organization> restExchange = oAuth2RestTemplate.postForEntity("http://organizationservice/organization/getname"
				, organizationServiceRequest, Organization.class, organizationId);
		return restExchange.getBody();
	}*/

}
