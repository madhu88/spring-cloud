package com.licensesservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.licensesservice.model.Organization;
import com.licensesservice.model.OrganizationServiceRequest;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {

	@PostMapping(value="/organization/getname", consumes="application/json")
	public Organization getOrganization(@RequestBody OrganizationServiceRequest organizationServiceRequest);

}
