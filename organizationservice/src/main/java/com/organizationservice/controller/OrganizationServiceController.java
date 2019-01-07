package com.organizationservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizationservice.model.request.OrganizationServiceRequest;
import com.organizationservice.model.response.OrganizationServiceResponse;

@RestController
@RequestMapping(value="/organization")
public class OrganizationServiceController {
	
	@PostMapping(value="/getname", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getOrgName(@RequestBody OrganizationServiceRequest organizationServiceRequest) {
		System.out.println("organizationServiceRequest.getOrgId = " + organizationServiceRequest.getOrgId());
		ResponseEntity<Object> responseEntity = null;
		OrganizationServiceResponse organizationServiceResponse = new OrganizationServiceResponse();
		organizationServiceResponse.setOrgId(organizationServiceRequest.getOrgId());
		organizationServiceResponse.setOrgName("Test name for org with ID " +  organizationServiceRequest.getOrgId());
		responseEntity = new ResponseEntity<>(organizationServiceResponse, HttpStatus.OK);
		return responseEntity;
	}

}
