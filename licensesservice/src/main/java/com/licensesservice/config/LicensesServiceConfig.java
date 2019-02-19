package com.licensesservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class LicensesServiceConfig {

	@Value("${redis.host}")
	private String redisServer;
	
	@Value("${redis.port}")
	private int redisPort;

	public String getRedisServer() {
		return redisServer;
	}

	public int getRedisPort() {
		return redisPort;
	}		
	
}
