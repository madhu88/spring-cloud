package com.licensesservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.licensesservice.clients.OrganizationDiscoveryClient;
import com.licensesservice.model.License;
import com.licensesservice.model.Organization;

@Service
public class LicenseService {
	
	@Autowired
	private OrganizationDiscoveryClient organizationDiscoveryClient;

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
		}
		
		return organization;
	}
}
