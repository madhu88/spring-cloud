package com.licensesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licensesservice.clients.OrganizationDiscoveryClient;
import com.licensesservice.clients.OrganizationFeignClient;
import com.licensesservice.clients.OrganizationRestTemplate;
import com.licensesservice.model.License;
import com.licensesservice.model.Organization;
import com.licensesservice.model.OrganizationServiceRequest;

@Service
public class LicenseService {
	
	@Autowired
	private OrganizationDiscoveryClient organizationDiscoveryClient;
	
	@Autowired
	private OrganizationRestTemplate organizationRestTemplate;
	
	@Autowired
	private OrganizationFeignClient organizationFeignClient;

	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = new License();
		license.setLicenseId(licenseId);
		Organization organization = retrieveOrgInfo(organizationId, clientType);
		license.setOrganizationId(Integer.toString(organization.getOrgId()));
		return license;
	}
		
	private Organization retrieveOrgInfo(String organizationId, String clientType) {
		Organization organization = null;
		
		switch (clientType) {
			case "discovery":
				System.out.println("Using dicovery client");
				organization = organizationDiscoveryClient.getOrganization(organizationId);
				break;
				
			case "rest":
				System.out.println("Using rest template client");
				organization = organizationRestTemplate.getOrganization(organizationId);
				break;
				
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
