package com.licensesservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licensesservice.clients.OrganizationDiscoveryClient;
import com.licensesservice.clients.OrganizationFeignClient;
import com.licensesservice.clients.OrganizationOAuth2RestTemplate;
import com.licensesservice.clients.OrganizationRestTemplate;
import com.licensesservice.model.License;
import com.licensesservice.model.Organization;
import com.licensesservice.model.OrganizationServiceRequest;
import com.licensesservice.utils.UserContextHolder;

@Service
public class LicenseService {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
	
	@Autowired
	private OrganizationDiscoveryClient organizationDiscoveryClient;
	
	@Autowired
	private OrganizationRestTemplate organizationRestTemplate;
	
	@Autowired
	private OrganizationFeignClient organizationFeignClient;
	
	@Autowired
	private OrganizationOAuth2RestTemplate organizationOAuth2RestTemplate;

	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = new License();
		license.setLicenseId(licenseId);
		Organization organization = retrieveOrgInfo(organizationId, clientType);
		license.setOrganizationId(Integer.toString(organization.getOrgId()));
		return license;
	}
		
	private Organization retrieveOrgInfo(String organizationId, String clientType) {
		Organization organization = null;
		
		logger.debug("tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		
		switch (clientType) {
			case "discovery":
				System.out.println("Using dicovery client");
				organization = organizationDiscoveryClient.getOrganization(organizationId);
				break;
				
			case "rest":
				System.out.println("Using rest template client");
				organization = organizationRestTemplate.getOrganization(organizationId);
				break;
			
			/*case "oauthrest":
				System.out.println("Using oAuth2 rest template client");
				organization = organizationOAuth2RestTemplate.getOrganization(organizationId);
				break;*/
				
			case "feign":
				System.out.println("Using feign client");
				OrganizationServiceRequest organizationServiceRequest = new OrganizationServiceRequest();
				organizationServiceRequest.setOrgId(Integer.parseInt(organizationId));
				organization = organizationFeignClient.getOrganization(organizationServiceRequest);
				break;						
				
			default:
				System.out.println("Using default rest template");
				organization = organizationRestTemplate.getOrganization(organizationId);
		}
		
		return organization;
	}
	
}
