package com.licensesservice.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.licensesservice.model.Organization;

@Repository
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {
	
	private static final String CACHE_NAME = "organization";
	
	private RedisTemplate<String, Organization> redisTemplate;
	private HashOperations hashOperations;
	
	public OrganizationRedisRepositoryImpl() {
		super();
	}
	
	@Autowired
	private OrganizationRedisRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void saveOrganization(Organization org) {
		hashOperations.put(CACHE_NAME, org.getOrgId(), org);		
	}

	@Override
	public void updateOrganization(Organization org) {
		hashOperations.put(CACHE_NAME, org.getOrgId(), org);		
	}

	@Override
	public void deleteOrganization(String organizationId) {
		hashOperations.delete(CACHE_NAME, Integer.parseInt(organizationId));
	}

	@Override
	public Organization findOrganization(String organizationId) {
		return (Organization) hashOperations.get(CACHE_NAME, Integer.parseInt(organizationId));
	}

}
