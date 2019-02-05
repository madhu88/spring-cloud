package com.organizationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ConfigEntries {

	@Value("${greet.timeout}")
	private Long greetTimeout;

	public Long getGreetTimeout() {
		return greetTimeout;
	}
		
}
