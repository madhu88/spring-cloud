package com.licensesservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.licensesservice.model.License;
import com.licensesservice.service.LicenseService;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);
	
	@Autowired
	private LicenseService licenseService;

	@RequestMapping(value="/{licenseId}/{clientType}",method = RequestMethod.GET)
    public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId,
                                          @PathVariable("clientType") String clientType) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.getAuthentication().getName();
		logger.debug("**** name " + securityContext.getAuthentication().getName());
		logger.debug("**** creds " + securityContext.getAuthentication().getCredentials());
        return licenseService.getLicense(organizationId,licenseId, clientType);
	}

}
