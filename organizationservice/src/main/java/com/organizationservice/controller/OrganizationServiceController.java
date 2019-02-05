package com.organizationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizationservice.config.ConfigEntries;
import com.organizationservice.model.request.OrganizationServiceRequest;
import com.organizationservice.model.response.OrganizationServiceResponse;

@RestController
@RequestMapping(value="/organization")
public class OrganizationServiceController {
	
	@Autowired
	private ConfigEntries configEntries;
	
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
	
	@PostMapping(value="/greet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> greetEmp(@RequestBody OrganizationServiceRequest organizationServiceRequest) {
		System.out.println("organizationServiceRequest.getOrgId = " + organizationServiceRequest.getOrgId());
		System.out.println("greetEmp timeOut = " + configEntries.getGreetTimeout());
		try {
			Thread.sleep(configEntries.getGreetTimeout());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseEntity<Object> responseEntity = null;
		OrganizationServiceResponse organizationServiceResponse = new OrganizationServiceResponse();
		organizationServiceResponse.setOrgId(organizationServiceRequest.getOrgId());
		organizationServiceResponse.setOrgName("Test name for org with ID " +  organizationServiceRequest.getOrgId());
		responseEntity = new ResponseEntity<>(organizationServiceResponse, HttpStatus.OK);
		return responseEntity;
	}

}
