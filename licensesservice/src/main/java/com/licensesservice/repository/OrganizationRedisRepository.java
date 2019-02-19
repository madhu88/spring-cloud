package com.licensesservice.repository;

import com.licensesservice.model.Organization;

public interface OrganizationRedisRepository {

	public void saveOrganization(Organization org);
	public void updateOrganization(Organization org);
	public void deleteOrganization(String organizationId);
	public Organization findOrganization(String organizationId);
	
}
