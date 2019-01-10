package com.confclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ServiceConfig {

	@Value("${example.property}")
	private String exampleProperty;
	
	@Value("${example.property.test}")
	private String examplePropertyTest;	

	public String getExampleProperty() {
		return exampleProperty;
	}
	
	public String getExamplePropertyTest() {
		return examplePropertyTest;
	}
}
