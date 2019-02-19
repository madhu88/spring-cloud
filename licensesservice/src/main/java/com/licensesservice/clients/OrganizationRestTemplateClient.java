package com.licensesservice.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.licensesservice.model.Organization;
import com.licensesservice.repository.OrganizationRedisRepository;
import com.licensesservice.utils.UserContextHolder;

@Component
public class OrganizationRestTemplateClient {

	/*@Autowired
	private RestTemplate restTemplate;*/
	
	@Autowired
	private OrganizationRedisRepository organizationRedisRepository;
	
	@Autowired
	private OrganizationRestTemplate organizationRestTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);
	
	private Organization checkRedisCache(String organizationId) {
		try {
			return organizationRedisRepository.findOrganization(organizationId);
		} catch (Exception ex) {
			logger.error("Error encountered while trying to retrieve organization {} check Redis Cache. Exception {}"
					, organizationId, ex);
		}
		return null;
	}
	
	private void cacheOrganizationObject(Organization org) {
		try {
			organizationRedisRepository.saveOrganization(org);
		} catch (Exception ex) {
			logger.error("Unable to cache organization {} in Redis Cache. Exception {}"
					, org.getOrgId(), ex);
		}
	}
	
	public Organization getOrganization(String organizationId) {
		logger.debug("**** In Licensing Service getOrganization: {}",
				UserContextHolder.getContext().getCorrelationId());
		Organization org = checkRedisCache(organizationId);
		
		if (null != org) {
			logger.debug("**** Successfully retrieved an organization {} from the redis cache: {}",
					organizationId, org);
			return org;
		}
		
		logger.debug("**** Unable to locate organization from the redis cache: {}.", organizationId);
		
		org = organizationRestTemplate.getOrganization(organizationId);
		
		if (null != org) {
			cacheOrganizationObject(org);
		}
		
		return org;
	}
}
