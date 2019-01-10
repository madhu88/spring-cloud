package com.confclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.confclient.config.ServiceConfig;

@RestController
public class ConfClientController {

	@Autowired
	private ServiceConfig serviceConfig;	
	
	@GetMapping(value = "/getconf/{key}")
	public String getConfData(@PathVariable("key") String key) {
		System.out.println("getExampleProperty = " + serviceConfig.getExampleProperty());
		System.out.println("getExamplePropertyTest = " + serviceConfig.getExamplePropertyTest());
		return serviceConfig.getExampleProperty() + " == " + serviceConfig.getExamplePropertyTest();
	}

}
