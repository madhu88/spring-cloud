package com.organizationservice2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organizationservice2.utils.UserContextHolder;
import com.organizationservice2.config.ConfigEntries;
import com.organizationservice2.model.request.OrganizationServiceRequest;
import com.organizationservice2.model.response.OrganizationServiceResponse;

@RestController
@RequestMapping(value="/v2/organization")
public class OrganizationService2Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService2Controller.class);
	
	@Autowired
	private ConfigEntries configEntries;
	
	@PostMapping(value="/getname", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getOrgName(@RequestBody OrganizationServiceRequest organizationServiceRequest) {
		logger.debug("organizationServiceRequest.getOrgId = " + organizationServiceRequest.getOrgId());
		ResponseEntity<Object> responseEntity = null;
		OrganizationServiceResponse organizationServiceResponse = new OrganizationServiceResponse();
		organizationServiceResponse.setOrgId(organizationServiceRequest.getOrgId());
		organizationServiceResponse.setOrgName("V2: Test name for org with ID " +  organizationServiceRequest.getOrgId());
		responseEntity = new ResponseEntity<>(organizationServiceResponse, HttpStatus.OK);
		logger.debug("tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		return responseEntity;
	}
	
	@PostMapping(value="/greet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> greetEmp(@RequestBody OrganizationServiceRequest organizationServiceRequest) {
		logger.debug("organizationServiceRequest.getOrgId = " + organizationServiceRequest.getOrgId());
		logger.debug("greetEmp timeOut = " + configEntries.getGreetTimeout());
		try {
			Thread.sleep(configEntries.getGreetTimeout());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseEntity<Object> responseEntity = null;
		OrganizationServiceResponse organizationServiceResponse = new OrganizationServiceResponse();
		organizationServiceResponse.setOrgId(organizationServiceRequest.getOrgId());
		organizationServiceResponse.setOrgName("V2: Test name for org with ID " +  organizationServiceRequest.getOrgId());
		responseEntity = new ResponseEntity<>(organizationServiceResponse, HttpStatus.OK);
		logger.debug("tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		return responseEntity;
	}

}
